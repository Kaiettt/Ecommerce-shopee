package com.ak.ecommerce_vender.domain.request;
import com.ak.ecommerce_vender.common.Gender;
import com.ak.ecommerce_vender.common.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SingupResponce {
    private String userName;
    private String fullName;
    private Role role;
    private boolean enable;
    private String phoneNumber;
    private Gender gender;
}
