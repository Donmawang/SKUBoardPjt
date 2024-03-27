package com.mysite.svp.repository;

import com.mysite.svp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUserId(String userId);
    User findByEmail(String email);
    User findByUserIdOrEmail(String userId, String email);
}
