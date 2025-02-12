package com.microservices.user.repository;

import com.microservices.user.entity.User;
import com.microservices.user.projection.UsernameAndEmailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    @Modifying
//    @Query("""
//            UPDATE User u
//            SET u.password = :newPassword
//            WHERE u.id = :id
//            """)
//    void updatePassword(Long id, String newPassword);

    @Query("""
            SELECT u.username AS username,
                   u.email AS email
            FROM User u
            WHERE u.username = :username OR u.email = :email
            """)
    List<UsernameAndEmailProjection> findExistingUsernameAndEmail(String username, String email);

}

