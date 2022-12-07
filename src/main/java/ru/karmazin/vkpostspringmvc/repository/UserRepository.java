package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.User;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
