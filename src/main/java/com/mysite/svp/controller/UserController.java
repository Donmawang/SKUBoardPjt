package com.mysite.svp.controller;

import com.mysite.svp.dto.UserDTO;
import com.mysite.svp.form.UserCreateForm;
import com.mysite.svp.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordRepeated())) {
            bindingResult.rejectValue("password",
                    "password.no_match", "비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            UserDTO userDTO = userServiceImpl.create(
                    userCreateForm.getUsername(),
                    userCreateForm.getPassword(),
                    userCreateForm.getEmail(),
                    userCreateForm.getLocalDateTime(),
                    userCreateForm.getRole()
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().equals("이미 존재하는 아이디입니다.")) {
                bindingResult.reject("signup Failed", "이미 존재하는 사용자입니다.");
                return "signup_form";
            }
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signup Failed", e.getMessage());
            return "signup_form";
        }


        return "redirect:/question/list";
    }

    @GetMapping("/login")
    public String login() {
        log.debug("Handling login request");
        return "login";
    }
}
