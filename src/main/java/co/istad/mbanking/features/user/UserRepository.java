package co.istad.mbanking.features.user;


import co.istad.mbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.hibernate.grammars.hql.HqlParser.FROM;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsByStudentIdCard(String studentIdCard);
//    JPQL
//    @Query(value = "SELECT * FROM users WHERE uuid = ?1 ", nativeQuery = true)
//    @Query("SELECT u FROM User As u  WHERE u.uuid = :uuid")
    Optional<User> findByUuid(String uuid);
}
