package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.VkAccount;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface VkAccountRepository extends JpaRepository<VkAccount, Long> {
    @Query(value = "select acc from VkAccount acc where acc.selected = true")
    VkAccount findBySelected();

    @Query(value = "select acc from VkAccount acc where acc.name = :name")
    VkAccount findByName(@Param("name") String name);
}
