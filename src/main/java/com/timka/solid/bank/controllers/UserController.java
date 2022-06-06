package com.timka.solid.bank.controllers;

import com.timka.solid.bank.entities.User;
import com.timka.solid.bank.repositories.UserRepository;
import com.timka.solid.bank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("users/{username}")
    public User findById(@PathVariable String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("NOT"));
        return user;
    }

    @GetMapping("users/save")
    public void save() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("$2a$12$OQjEIkxNrhNHHhIfmJh1HOip1pBVJPQu2tmQyMq1qLVPmTp1iTD9K");
        userRepository.save(user);

    }
}
