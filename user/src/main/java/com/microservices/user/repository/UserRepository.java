package com.microservices.user.repository;

import com.microservices.user.entity.User;
import com.microservices.user.projection.UsernameAndEmailProjection;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Timed("existsByUsername")
    boolean existsByUsername(String username);

    @Timed("existsByEmail")
    boolean existsByEmail(String email);

    @Timed("findExistingUsernameAndEmail")
    @Query("""
            SELECT u.username AS username,
                   u.email AS email
            FROM User u
            WHERE u.username = :username OR u.email = :email
            """)
    List<UsernameAndEmailProjection> findExistingUsernameAndEmail(String username, String email);

}

