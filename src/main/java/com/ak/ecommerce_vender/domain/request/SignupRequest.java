package com.ak.ecommerce_vender.domain.request;

import com.ak.ecommerce_vender.common.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private Gender gender;
    private String phoneNumber;
}
