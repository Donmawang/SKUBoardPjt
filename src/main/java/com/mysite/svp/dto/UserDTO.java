package com.mysite.svp.dto;

import com.mysite.svp.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String password;
    private String email;
    private LocalDateTime localDateTime;
    private String role;

    @Builder
    public UserDTO(String userId, String password, LocalDateTime localDateTime, String email, String role) {
        this.userId = userId;
        this.password = password;
        this.localDateTime = localDateTime;
        this.email = email;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .email(email)
                .localDateTime(localDateTime)
                .role(role)
                .build();
    }

    public UserDTO fromEntity(UserDTO user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
