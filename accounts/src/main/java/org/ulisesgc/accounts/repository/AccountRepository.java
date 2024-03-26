package org.ulisesgc.accounts.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.ulisesgc.accounts.entity.Accounts;
import org.ulisesgc.accounts.entity.Customer;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
