package co.istad.mbanking.mapper;

import co.istad.mbanking.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.Transaction.dto.TransactionResponse;
import co.istad.mbanking.domain.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {AccountMapper.class})
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);
    Transaction fromTransactionCreateRequest(TransactionCreateRequest transactionCreateRequest);
}
