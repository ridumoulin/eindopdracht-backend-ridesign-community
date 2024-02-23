package com.ridesigncommunity.RiDesignCommunity.repository;

import com.ridesigncommunity.RiDesignCommunity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

}
