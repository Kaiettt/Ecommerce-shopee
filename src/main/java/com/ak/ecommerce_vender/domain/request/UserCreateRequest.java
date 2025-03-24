package com.ak.ecommerce_vender.domain.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import com.ak.ecommerce_vender.common.Gender;
import com.ak.ecommerce_vender.common.Role;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {
    private String name; 
    private String email;
    private String password;
    private String profilePicture;
    private LocalDateTime birthDate;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private Role role;
}
