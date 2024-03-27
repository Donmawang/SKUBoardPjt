package com.mysite.svp.response;

import com.mysite.svp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String message;
    private User user;
}
