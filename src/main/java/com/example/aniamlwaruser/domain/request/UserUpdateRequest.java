package com.example.aniamlwaruser.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String id;
    private String password;
    private String nickName;
    private String profileImage;
}