package co.istad.mbanking.features.Transaction;

import co.istad.mbanking.features.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.Transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface TransactionService {
    TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest);
    Page<TransactionResponse> findAll(int page, int size, String sortDirection ,String transactionType);
}
