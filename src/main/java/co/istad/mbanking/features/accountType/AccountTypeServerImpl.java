package co.istad.mbanking.features.accountType;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import co.istad.mbanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public  class AccountTypeServerImpl implements AccountTypeService{
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public List<AccountTypeResponse> findList() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypeMapper.toListAccountTypesResponse(accountTypes);
    }

    @Override
    public AccountTypeResponse findAccountTypeByAlias(String alias) {
        AccountType accountType = accountTypeRepository.findByAlias(alias).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account does not exits with "+alias+" alias"
                )
        );
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
}
