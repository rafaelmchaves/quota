package com.vicarius.quota.services.impl;

import com.vicarius.quota.dao.UserRepository;
import com.vicarius.quota.exceptions.UserNotFoundException;
import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @CachePut(value = "users", key = "#result.id")
    @Override
    public User create(User user) {

        user.setCreation(LocalDateTime.now());
        user.setStatus(Status.ACTIVE);

        return this.userRepository.save(user);
    }

    @CachePut(value = "users", key = "#result.id")
    @Override
    public User update(User user) {

        user.setUpdate(LocalDateTime.now());

        var foundUser = get(user.getId());
        if (foundUser == null) {
            throw new UserNotFoundException("User not found");
        }

        foundUser.setStatus(user.getStatus());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setUpdate(LocalDateTime.now());

        return this.userRepository.update(foundUser);
    }

    @Cacheable("users")
    @Override
    public User get(String id) {
        return userRepository.get(UUID.fromString(id));
    }

    @CachePut(value = {"users"})
    @Transactional
    @Override
    public void delete(String id) {
        this.userRepository.delete(UUID.fromString(id));
    }

    @Cacheable("users")
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @CachePut("users")
    public void lockUser(User user) {

        user.setStatus(Status.LOCKED);
        user.setUpdate(LocalDateTime.now());

        this.userRepository.update(user);
    }
}
