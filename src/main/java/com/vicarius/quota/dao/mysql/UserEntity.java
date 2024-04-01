package com.vicarius.quota.dao.mysql;

import com.vicarius.quota.model.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_login_time_utc")
    private LocalDateTime lastLoginTimeUtc;

    @Column(name = "creation_date_time")
    private LocalDateTime creation;

    @Column(name = "update_date_time")
    private LocalDateTime update;

    @Enumerated(EnumType.STRING)
    private Status status;

}
