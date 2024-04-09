package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }

    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }
//    @GetMapping
//    List<AccountResponse> findAll() {
//        return accountService.findAll();
//    }
    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo (@PathVariable String actNo, @RequestBody AccountRenameRequest accountRenameRequest) {
        return accountService.renameByActNo(actNo, accountRenameRequest);
    }
    @PutMapping("/{actNo}/hide")
    void hiddenAccounts(@PathVariable String actNo) {
        accountService.hiddenAccounts(actNo);
    }
    @GetMapping
    Page<AccountResponse> findList(@RequestParam (required = false , defaultValue = "0") int page,
                                   @RequestParam (required = false, defaultValue = "25") int size) {
        return accountService.findList(page, size);
    }
}

