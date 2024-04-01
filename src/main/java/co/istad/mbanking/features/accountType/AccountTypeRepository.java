package co.istad.mbanking.features.accountType;

import co.istad.mbanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType ,Long> {

    Optional<AccountType> findByAlias(String alias);
}
