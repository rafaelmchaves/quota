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

    private final UserResponseMapper userResponseMapper;

    @PostMapping("/users")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {

        final var user = this.userService.create(User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName()).build()
        );

        return new ResponseEntity<>(userResponseMapper.convert(user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/users/{id}")
    ResponseEntity<UserResponse> update(@PathVariable String id, @RequestBody UserRequest userRequest) {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    ResponseEntity<UserResponse> get(@PathVariable String id) {

        final var user = userResponseMapper.convert(this.userService.get(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> delete(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
