package com.vicarius.quota.repository.mysql;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.UserBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service(MySqlImplementation.IMPLEMENTATION_ID)
public class MySqlImplementation implements UserBoundary {
    public static final String IMPLEMENTATION_ID = "MySqlImplementation";

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {;
        final var userEntity = userEntityMapper.convert(user);
        userEntity.setId(UUID.randomUUID());
        final var result = userRepository.save(userEntity);
        return userEntityMapper.convert(result);
    }

    @Override
    public User get(UUID id) {
        return userEntityMapper.convert(userRepository.findById(id).orElse(null));
    }

    @Override
    public User update(User user) {
        final var userEntity = userEntityMapper.convert(user);
        if (userEntity.getId() == null) {
            userEntity.setId(UUID.randomUUID());
        }
        final var result = userRepository.save(userEntity);
        return userEntityMapper.convert(result);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        final var users = userRepository.findAll();
        return userEntityMapper.convert(users);
    }
}
