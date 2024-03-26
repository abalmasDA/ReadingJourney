package com.readingjourney.account.repository;

import com.readingjourney.account.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on User entities. This interface provides
 * methods for accessing and managing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
