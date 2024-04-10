package co.istad.mbanking.features.Transaction;

import co.istad.mbanking.features.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.Transaction.dto.TransactionResponse;
import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.Transaction;
import co.istad.mbanking.features.account.AccountRepository;
import co.istad.mbanking.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private  final TransactionRepository transactionRepository;
    private  final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest) {

        // validate owner account number
        Account owner = accountRepository.findByActNo(transactionCreateRequest.ownerActNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Owner account number not found"
                ));

        Account transferReceiver = accountRepository.findByActNo(transactionCreateRequest.transferReceiverActNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transfer receiver account number not found"
                ));

        // check amount transfer ( amount  <= balance) (act owner )
        if (owner.getBalance().doubleValue() < transactionCreateRequest.amount().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance"
            );
        }

        // check transfer to the same account
        if (owner.getActNo().equals(transferReceiver.getActNo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transfer to the same account is not allowed"
            );
        }

        // check amount with transfer limit
        if (transactionCreateRequest.amount().doubleValue() >= owner.getTransferLimit().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transfer limit exceeded"
            );
        }

        // update owner balance
        owner.setBalance(owner.getBalance().subtract(transactionCreateRequest.amount()));

        // update transfer receiver balance
        transferReceiver.setBalance(transferReceiver.getBalance().add(transactionCreateRequest.amount()));

        // save transaction
        Transaction transaction = transactionMapper.fromTransactionCreateRequest(transactionCreateRequest);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(transferReceiver);
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus(true);
        transactionRepository.save(transaction);

        return  transactionMapper.toTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> findAll(int page, int size, String sortDirection, String transactionType) {
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero");
        }

        if (size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size must be greater than or equal to one");
        }

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortDirection.equalsIgnoreCase("ASC")) {
            direction = Sort.Direction.ASC;
        }

        Sort sortByTransactionAt = Sort.by(direction, "transactionAt");
        PageRequest pageRequest = PageRequest.of(page, size, sortByTransactionAt);

        Page<Transaction> transactionsPage;
        if ("all".equalsIgnoreCase(transactionType)) {
            transactionsPage = transactionRepository.findAll(pageRequest);
        } else {
            transactionsPage = transactionRepository.findByTransactionType(transactionType, pageRequest);
        }

        return transactionsPage.map(transactionMapper::toTransactionResponse);
    }

}
