package com.timka.solid.bank.repositories;

import com.timka.solid.bank.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
     Optional<User> findByUsername(String username);
}
