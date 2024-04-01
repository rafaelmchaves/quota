package com.vicarius.quota.dao.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMySqlJpaRepository extends JpaRepository<UserEntity, UUID> {
}
