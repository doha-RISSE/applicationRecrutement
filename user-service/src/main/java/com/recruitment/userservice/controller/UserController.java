package com.recruitment.userservice.controller;

import com.recruitment.userservice.dto.LoginRequest;
import com.recruitment.userservice.dto.RegisterRequest;
import com.recruitment.userservice.model.User;
import com.recruitment.userservice.service.UserService;
import com.recruitment.userservice.security.JwtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final JwtService jwtService;

    public UserController(UserService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {
        User user = new User(req.name, req.email, req.password, req.role);
        return service.register(user);
    }

    // LOGIN (retourne JWT)
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {

        return service.login(req.email, req.password)
                .map(u -> jwtService.generateToken(u.getId(), u.getEmail(), u.getRole()))
                .orElse("INVALID CREDENTIALS");
    }

    // UPDATE PROFILE (RESTE INTACT)
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updated) {
        updated.setId(id);
        return service.register(updated);
    }
}