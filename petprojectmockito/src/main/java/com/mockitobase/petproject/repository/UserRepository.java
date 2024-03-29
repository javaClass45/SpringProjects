package com.mockitobase.petproject.repository;

import com.mockitobase.petproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
}
