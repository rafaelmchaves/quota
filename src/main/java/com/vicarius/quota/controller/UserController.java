package com.vicarius.quota.controller;

import com.vicarius.quota.controller.request.UserRequest;
import com.vicarius.quota.controller.request.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/users")
public class UserController {

    @PostMapping(consumes = "application/json")
    ResponseEntity<Void> create(@RequestBody UserRequest userRequest) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserRequest userRequest) {

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
