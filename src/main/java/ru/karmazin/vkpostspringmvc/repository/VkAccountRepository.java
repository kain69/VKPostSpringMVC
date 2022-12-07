package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.VkAccount;

import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface VkAccountRepository extends JpaRepository<VkAccount, Long> {
    Optional<VkAccount> findByName(String name);
}
