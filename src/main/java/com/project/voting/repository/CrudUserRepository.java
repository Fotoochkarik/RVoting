package com.project.voting.repository;

import com.project.voting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
