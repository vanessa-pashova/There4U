package com.example.there4u.controller;

import com.example.there4u.dto.RegularUserDto;
import com.example.there4u.dto.RegularUserRequest;
import com.example.there4u.service.registration_and_login.RegularUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/users")
public class RegularUserController {
    private final RegularUserService regularUserService;

    private RegularUserController(RegularUserService regularUserService) {
        this.regularUserService = regularUserService;
    }

    @PostMapping
    public ResponseEntity<RegularUserDto> createItem(@Valid @RequestBody RegularUserRequest request) {
        log.info("Create Item API POST");
        RegularUserDto created = regularUserService.createRegularUser(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
