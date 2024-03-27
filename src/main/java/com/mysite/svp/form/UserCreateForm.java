package com.mysite.svp.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordRepeated;

    private LocalDateTime localDateTime = LocalDateTime.now();

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email
    private String email;

    // default role은 user로 설정
    private String role = "user";
}
