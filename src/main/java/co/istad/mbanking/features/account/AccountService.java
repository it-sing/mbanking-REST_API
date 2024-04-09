package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    void hiddenAccounts(String actNo);
    void  createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);
//    List<AccountResponse> findAll();
    AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest);
    Page<AccountResponse> findList(int page , int size);
}
