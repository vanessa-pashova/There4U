package com.example.there4u.controller;

import com.example.there4u.dto.NGOUser.NGODto;
import com.example.there4u.dto.NGOUser.NGOUserEditProfileRequest;
import com.example.there4u.dto.NGOUser.NGOUserRegisterRequest;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.service.general_for_users.NGOService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ngo-users")
public class NGOUserController {
    private final NGOService ngoService;

    public NGOUserController(NGOService ngoService) {
        this.ngoService = ngoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NGOUser> getNGOUser(@PathVariable Long id) {
        NGOUser user = ngoService.findNGOUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NGODto> createItem(@Valid @RequestBody NGOUserRegisterRequest request) {
        NGODto created = ngoService.createNGO(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NGODto> updateItem(@PathVariable Long id, @Valid @RequestBody NGOUserEditProfileRequest request) {
        NGOUser user = ngoService.findNGOUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        NGODto updated = ngoService.updateNGOUser(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        NGOUser user = ngoService.findNGOUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ngoService.deleteNGO(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
