package com.vicarius.quota.repository.mysql;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service(MySqlImplementation.IMPLEMENTATION_ID)
public class MySqlImplementation implements DatabaseInterface {
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
}
