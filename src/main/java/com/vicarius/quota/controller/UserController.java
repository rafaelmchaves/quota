package com.vicarius.quota.controller;

import com.vicarius.quota.controller.mapper.QuotaMapper;
import com.vicarius.quota.controller.mapper.UserRequestMapper;
import com.vicarius.quota.controller.mapper.UserResponseMapper;
import com.vicarius.quota.controller.request.UserRequest;
import com.vicarius.quota.controller.request.UserUpdateRequest;
import com.vicarius.quota.controller.response.QuotaResponse;
import com.vicarius.quota.controller.response.UserResponse;
import com.vicarius.quota.model.User;
import com.vicarius.quota.services.QuotaService;
import com.vicarius.quota.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
public class UserController {

    private final UserService userService;

    private final QuotaService quotaService;

    private final UserResponseMapper userResponseMapper;

    private final UserRequestMapper userRequestMapper;

    private final QuotaMapper quotaMapper;

    @PostMapping("/users")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        final var user = this.userService.create(User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName()).build()
        );

        return new ResponseEntity<>(userResponseMapper.convert(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    ResponseEntity<UserResponse> update(@PathVariable String id, @RequestBody UserUpdateRequest userRequest) {
        final var user = this.userRequestMapper.convert(userRequest);
        user.setId(id);
        final var userResponse = userResponseMapper.convert(this.userService.update(user));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    ResponseEntity<UserResponse> get(@PathVariable String id) {
        final var user = userResponseMapper.convert(this.userService.get(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    ResponseEntity<UserResponse> delete(@PathVariable String id) {
        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/users/{id}/consume-quota")
    ResponseEntity<Void> consumeQuota(@PathVariable String id) {
        quotaService.consumeQuota(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/quota")
    ResponseEntity<List<QuotaResponse>> getUsersQuota() {
        final var quotas = this.quotaService.getAll();
        return new ResponseEntity<>(this.quotaMapper.convert(quotas), HttpStatus.OK);
    }
}
