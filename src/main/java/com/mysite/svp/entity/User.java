package com.mysite.svp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
    // id를 기본키로 설정하돼, 자동으로 생성되지 않고 직접 입력하도록 설정
    @Id
    @Column(name="userId", unique = true) // userId가 중복되지 않도록 설정
    private String userId;
    private String password;
    @Column(name="email", unique = true) // email이 중복되지 않도록 설정
    private String email;
    // default role은 user로 설정
    @Column(name="role", columnDefinition = "varchar(255) default 'user'")
    private String role;
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Builder
    public User(String userId, String password, String email,
                LocalDateTime localDateTime, String role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.localDateTime = localDateTime;
        this.role = role;
    }
}
