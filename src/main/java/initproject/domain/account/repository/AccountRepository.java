package initproject.domain.account.repository;

import initproject.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select account from Account account " +
            "join account.roleList where account.email = :email")
    Optional<Account> findByEmailWithRole(@Param("email") String email);
}
