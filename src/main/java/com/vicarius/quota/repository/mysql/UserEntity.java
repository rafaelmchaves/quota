package com.vicarius.quota.repository.mysql;

import com.vicarius.quota.model.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_login_time_utc")
    private LocalDateTime lastLoginTimeUtc;

    private LocalDateTime creation;

    private LocalDateTime update;

    private Status status;

}
