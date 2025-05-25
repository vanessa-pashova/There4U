package com.example.there4u.controller.login;

import com.example.there4u.dto.login.LoginRequest;
import com.example.there4u.dto.login.LoginResponse;
import com.example.there4u.model.user.User;
import com.example.there4u.service.jwt.JWTService;
import com.example.there4u.service.login.LoginService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final JWTService jwtService;

    private final LoginService loginService;

    public LoginController(LoginService loginService, JWTService jwtService) {
        this.jwtService = jwtService;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = loginService.authenticate(loginRequest);
            String jwtToken = jwtService.generateToken(user);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
