package com.powernode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginSuccess {
    //token的值
    private String accessToken;
    private String expiresIn;
    private String type;
}
