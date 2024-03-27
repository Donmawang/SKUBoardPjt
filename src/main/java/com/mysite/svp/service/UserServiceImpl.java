package com.mysite.svp.service;

import com.mysite.svp.dto.UserDTO;
import com.mysite.svp.entity.User;
import com.mysite.svp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(String username, String password, String email, LocalDateTime localDateTime, String role) {
        if (userRepository.findByUserId(username) != null) {
            throw new DataIntegrityViolationException("이미 존재하는 아이디입니다.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(username);
        userDTO.setPassword(passwordEncoder.encode(password));
        userDTO.setEmail(email);
        userDTO.setLocalDateTime(localDateTime);
        userDTO.setRole(role);

        userRepository.save(userDTO.toEntity());

        return userDTO;
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUserId(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        } else
            return user.get();
    }
}
