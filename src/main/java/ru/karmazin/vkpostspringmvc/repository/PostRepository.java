package ru.karmazin.vkpostspringmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karmazin.vkpostspringmvc.model.Post;

/**
 * @author Vladislav Karmazin
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
