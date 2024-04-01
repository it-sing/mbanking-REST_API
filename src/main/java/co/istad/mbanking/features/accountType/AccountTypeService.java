package co.istad.mbanking.features.accountType;

import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findList();
    AccountTypeResponse findAccountTypeByAlias(String alias);
}
