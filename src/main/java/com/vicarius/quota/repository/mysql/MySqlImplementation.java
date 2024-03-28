package com.vicarius.quota.repository.mysql;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service(MySqlImplementation.IMPLEMENTATION_ID)
public class MySqlImplementation implements DatabaseInterface {
    public static final String IMPLEMENTATION_ID = "MySqlImplementation";

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        final var userEntity = UserEntity.builder().firstName(user.getFirstName()).lastName(user.getLastName()).status(user.getStatus()).build();
        final var result = userRepository.save(userEntity);
        return User.builder().build();
    }
}
