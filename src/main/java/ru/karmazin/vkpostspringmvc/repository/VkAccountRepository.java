package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.VkAccount;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface VkAccountRepository extends JpaRepository<VkAccount, Long> {
    Optional<VkAccount> findByName(String name);
    Optional<VkAccount> findBySelected(Boolean selected);
    List<VkAccount> findAllBySelected(Boolean selected);
}
