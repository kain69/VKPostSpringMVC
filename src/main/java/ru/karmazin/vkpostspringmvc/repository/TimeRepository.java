package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.Time;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
}
