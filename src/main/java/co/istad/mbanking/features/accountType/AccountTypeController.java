package co.istad.mbanking.features.accountType;

import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AccountTypeResponse> findList(){
        return accountTypeService.findList();
    }

    @GetMapping("/{alias}")
    @ResponseStatus(HttpStatus.OK)
    AccountTypeResponse findAccountTypeByAlias(@PathVariable  String alias){
        return accountTypeService.findAccountTypeByAlias(alias);
    }
}