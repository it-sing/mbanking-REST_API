package co.istad.mbanking.init;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.domain.Role;
import co.istad.mbanking.features.accountType.AccountTypeRepository;
import co.istad.mbanking.features.cardType.CardTypeRepository;
import co.istad.mbanking.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CardTypeRepository cardTypeRepository;

    @PostConstruct
    void initRole() {

        // Auto generate role (USER, CUSTOMER, STAFF, ADMIN)
        if (roleRepository.count() < 1) {
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(
                    List.of(user, customer, staff, admin)
            );
        }
    }
    @PostConstruct
    void initAccountType(){
        if(accountTypeRepository.count() < 1) {
            AccountType savingActType = new AccountType();
            savingActType.setName("Saving Account");
            savingActType.setAlias("saving-account");
            savingActType.setIsDeleted(false);
            savingActType.setDescription("This is Saving Account");
            accountTypeRepository.save(savingActType);

            AccountType payrollActType = new AccountType();
            payrollActType.setName("Payrolling Account");
            payrollActType.setAlias("payroll-account");
            payrollActType.setIsDeleted(false);
            payrollActType.setDescription("This is Payroll Account");
            accountTypeRepository.save(payrollActType);

            AccountType cardActAccount = new AccountType();
            cardActAccount.setName("Card Account");
            cardActAccount.setAlias("card-account");
            cardActAccount.setIsDeleted(false);
            cardActAccount.setDescription("This is Card Account");
            accountTypeRepository.save(cardActAccount);


        }
    }
    @PostConstruct
    void initCardTypes(){
        if(cardTypeRepository.count() < 1) {
            CardType visaCardType = new CardType();
            visaCardType.setName("Visa");
            visaCardType.setIsDeleted(false);
            cardTypeRepository.save(visaCardType);

            CardType masterCardType = new CardType();
            masterCardType.setName("MasterCard");
            masterCardType.setIsDeleted(false);
            cardTypeRepository.save(masterCardType);

        }
    }

}
