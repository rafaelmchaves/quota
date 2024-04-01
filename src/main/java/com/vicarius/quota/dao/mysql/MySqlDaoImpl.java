package com.vicarius.quota.dao.mysql;

import com.vicarius.quota.model.User;
import com.vicarius.quota.dao.strategy.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service(MySqlDaoImpl.IMPLEMENTATION_ID)
public class MySqlDaoImpl implements UserDao {
    public static final String IMPLEMENTATION_ID = "MySqlImplementation";

    private final UserMySqlJpaRepository userMySqlJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {;
        final var userEntity = userEntityMapper.convert(user);
        userEntity.setId(UUID.randomUUID());
        final var result = userMySqlJpaRepository.save(userEntity);
        return userEntityMapper.convert(result);
    }

    @Override
    public User get(UUID id) {
        return userEntityMapper.convert(userMySqlJpaRepository.findById(id).orElse(null));
    }

    @Override
    public User update(User user) {
        final var userEntity = userEntityMapper.convert(user);
        if (userEntity.getId() == null) {
            userEntity.setId(UUID.randomUUID());
        }
        final var result = userMySqlJpaRepository.save(userEntity);
        return userEntityMapper.convert(result);
    }

    @Override
    public void delete(UUID id) {
        userMySqlJpaRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        final var users = userMySqlJpaRepository.findAll();
        return userEntityMapper.convert(users);
    }
}
