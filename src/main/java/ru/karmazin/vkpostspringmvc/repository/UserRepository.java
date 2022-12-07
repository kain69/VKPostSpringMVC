package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.User;

import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
