package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String id;
    private String password;
    private String nickName;
    private String profileImage;
    private Species species;
}
