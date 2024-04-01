package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAllAccountTypes();
    AccountTypeResponse findAccountTypeByAlias(String alias);
}
