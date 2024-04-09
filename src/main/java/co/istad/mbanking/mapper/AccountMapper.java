package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.domain.UserAccount;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.account.dto.AccountSnippetResponse;
import co.istad.mbanking.features.user.dto.UserDetailsResponse;
import co.istad.mbanking.features.user.dto.UserResponse;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {
        UserMapper.class,
        AccountTypeMapper.class
})
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);
    @Mapping(source = "userAccountList" , target = "user", qualifiedByName = "mapUserResponse")
    AccountResponse toAccountResponse(Account account);
//    @Mapping(source = "userAccountList",target = "user",qualifiedByName ="mapUserResponse" )
    List<AccountResponse> toAccountResponseList(List<Account> account);
    UserResponse toUserResponse(User user);
    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccountList){
        return toUserResponse(userAccountList.get(0).getUser());
    }

    AccountSnippetResponse toAccountSnippetResponse(Account account);

}
