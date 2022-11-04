package com.free.persistence;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.free.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByUsername(String username);

    // OAuth 
    Optional<User> findByEmail(String email);

    // userget
    User findByNickname(String nickname);

    
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
