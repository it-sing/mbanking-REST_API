package co.istad.mbanking.Transaction;

import co.istad.mbanking.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
