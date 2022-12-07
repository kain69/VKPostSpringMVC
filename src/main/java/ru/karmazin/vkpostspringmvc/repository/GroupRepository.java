package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.Group;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
