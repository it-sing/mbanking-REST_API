package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.domain.UserAccount;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.accountType.AccountTypeRepository;
import co.istad.mbanking.features.user.UserRepository;
import co.istad.mbanking.mapper.AccountMapper;
import co.istad.mbanking.mapper.AccountTypeMapper;
import co.istad.mbanking.mapper.UserMapper;
import co.istad.mbanking.util.RandomUtil;
import jakarta.transaction.Transactional;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final AccountTypeMapper accountTypeMapper;


    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {

        // check account type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Invalid account type"));

        // check user by UUID
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found"));

        // map account dto to account entity
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo(RandomUtil.generate9Digits());
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());

        userAccountRepository.save(userAccount);
    }

    @Override
    public AccountResponse findByActNo(String actNo) {

        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,

                                "Account not found"
                        ));
        return accountMapper.toAccountResponse(account);
    }

//    @Override
//    public List<AccountResponse> findAll() {
//        List<Account> accounts = accountRepository.findAll();
//        return accountMapper.toAccountResponseList(accounts);
//    }
    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Account not found"
                        ));
        if (account.getAlias().equals(accountRenameRequest.newName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Alias is the same as the current one");
        }
        account.setAlias(accountRenameRequest.newName());
        account = accountRepository.save(account);

        return accountMapper.toAccountResponse( account);

    }
    @Transactional
    @Override
    public void hiddenAccounts(String actNo) {
        if(!accountRepository.existsByActNo(actNo)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account not found");
        }
        try {
            accountRepository.hideAccountByActNo(actNo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while hiding account");
        }
    }
    @Override
    public Page<AccountResponse> findList(int page, int size) {
        if (page < 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page number must be greater than 0");
        }
        if (size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size must be greater than 0");
        }
        Sort sortByActName = Sort.by(Sort.Order.asc("actName"));
        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);
        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }
}