package com.recruitment.userservice.controller;

import com.recruitment.userservice.dto.LoginRequest;
import com.recruitment.userservice.dto.RegisterRequest;
import com.recruitment.userservice.model.User;
import com.recruitment.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {
        User user = new User(req.name, req.email, req.password, req.role);
        return service.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return service.login(req.email, req.password)
                .map(u -> "LOGIN SUCCESS")
                .orElse("INVALID CREDENTIALS");
    }

    // UPDATE PROFILE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updated) {
        updated.setId(id);
        return service.register(updated);
    }
}