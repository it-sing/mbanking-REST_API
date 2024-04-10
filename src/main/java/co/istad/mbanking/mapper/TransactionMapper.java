package co.istad.mbanking.mapper;

import co.istad.mbanking.features.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.Transaction.dto.TransactionResponse;
import co.istad.mbanking.domain.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {AccountMapper.class})
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);
    Transaction fromTransactionCreateRequest(TransactionCreateRequest transactionCreateRequest);
    List<TransactionResponse> toTransactionResponse (List<Transaction> transaction);
}
