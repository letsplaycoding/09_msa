package com.ohgiraffers.userservice.aggregate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 80, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false,unique = true)
    private String encryptedPwd;
}
