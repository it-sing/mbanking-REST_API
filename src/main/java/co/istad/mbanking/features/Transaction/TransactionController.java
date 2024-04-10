package co.istad.mbanking.features.Transaction;

import co.istad.mbanking.features.Transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.Transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    TransactionResponse transfer(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) {
        return transactionService.transfer(transactionCreateRequest);
    }

    @GetMapping
    Page<TransactionResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size,
            @RequestParam (required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false, defaultValue = "ALL") String transactionType
    ){
        return transactionService.findAll(page, size, sortDirection , transactionType);
    }
}
