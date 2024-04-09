package co.istad.mbanking.Transaction;

import co.istad.mbanking.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.Transaction.dto.TransactionResponse;
import co.istad.mbanking.domain.Transaction;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface TransactionService {
    TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest);
    Page<TransactionResponse> findList(LocalDateTime transactionAt ,  String transactionType);
}
