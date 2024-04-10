package co.istad.mbanking.features.Transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateRequest(
        @NotBlank(message = "account no is required")
        String ownerActNo,

        @NotBlank(message = "account no is required")
        String transferReceiverActNo,

        @NotNull(message = "Amount is required")
        @Positive
        BigDecimal amount,


        String remark

) {
}
