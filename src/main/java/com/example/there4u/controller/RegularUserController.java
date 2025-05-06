package com.example.there4u.controller;

import com.example.there4u.dto.RegularUser.RegularUserDto;
import com.example.there4u.dto.RegularUser.RegularUserEditProfileRequest;
import com.example.there4u.dto.RegularUser.RegularUserRegisterRequest;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.service.general_for_users.RegularUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/regular-users")
public class RegularUserController {
    private final RegularUserService regularUserService;

    public RegularUserController(RegularUserService regularUserService) {
        this.regularUserService = regularUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegularUser> getRegularUser(@PathVariable Long id) {
        RegularUser user = regularUserService.findRegularUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("by-username")
    public ResponseEntity<RegularUser> getRegularUserByUsername(@RequestParam String username) {
        RegularUser user = regularUserService.findRegularUserByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("by-email")
    public ResponseEntity<RegularUser> getRegularUserByEmail(@RequestParam String email) {
        RegularUser user = regularUserService.findRegularUserByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegularUserDto> createItem(@Valid @RequestBody RegularUserRegisterRequest request) {
        RegularUserDto created = regularUserService.createRegularUser(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegularUserDto> updateItem(@PathVariable Long id, @Valid @RequestBody RegularUserEditProfileRequest request) {
        RegularUser user = regularUserService.findRegularUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RegularUserDto updated = regularUserService.updateRegularUser(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        RegularUser user = regularUserService.findRegularUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        regularUserService.deleteRegularUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
