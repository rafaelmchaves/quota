package com.vicarius.quota.controller;

import com.vicarius.quota.controller.request.UserRequest;
import com.vicarius.quota.controller.request.UserResponse;
import com.vicarius.quota.model.User;
import com.vicarius.quota.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {

        this.userService.create(User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName()).build()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    ResponseEntity<UserResponse> update(@PathVariable String id, @RequestBody UserRequest userRequest) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<UserResponse> get(@PathVariable String id) {

        return new ResponseEntity<>(new UserResponse(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> delete(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
