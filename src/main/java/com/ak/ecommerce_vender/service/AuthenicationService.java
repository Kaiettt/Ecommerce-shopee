package com.ak.ecommerce_vender.service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.common.Role;
import com.ak.ecommerce_vender.domain.entity.ConfirmationToken;
import com.ak.ecommerce_vender.domain.entity.User;
import com.ak.ecommerce_vender.domain.request.SignupRequest;
import com.ak.ecommerce_vender.domain.request.SingupResponce;
import com.ak.ecommerce_vender.domain.responce.LoginResponce;
import com.ak.ecommerce_vender.error.EmailAlreadyExistsException;
import com.ak.ecommerce_vender.error.UserNotEnabledException;
import com.ak.ecommerce_vender.error.VerificationException;
import com.ak.ecommerce_vender.util.SecurityUtil;
@AllArgsConstructor
@Service
public class AuthenicationService {
    private EmailServiceImpl emailServiceImpl;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private PasswordEncoder passwordEncoder;
    private ConfirmationService confirmationService;
    public LoginResponce handleLoginResponce(Authentication authentication, String username)throws UserNotEnabledException{
        User user = this.userService.getUserByUserName(username);
        // if(!user()){
        //     throw new UserNotEnabledException("User is not enabled");
        // }
        LoginResponce.UserLogin userLogin = new LoginResponce.UserLogin();
        userLogin.setFullName(user.getName());
        userLogin.setId(user.getId());
        userLogin.setRole(user.getRole());
        userLogin.setUserName(username);
        String accessToken = this.securityUtil.createToken(username,user);
        String refreshToken = this.securityUtil.createRefreshToken(username, user);
        user.setRefreshToken(refreshToken);
        this.userService.updateUser(user);

        ResponseCookie springCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtil.jwtRefreshTokenExpiration)
                .build();
        LoginResponce loginResponce = LoginResponce.builder()
            .accessToken(accessToken)
            .springCookie(springCookie)
            .user(userLogin)
            .build();
        return loginResponce;
    }
    public LoginResponce getAccessToken(String refresh_token) throws EntityExistsException{
        User user = this.userService.getUserByRefreshToken(refresh_token);
        LoginResponce.UserLogin userResponce = new LoginResponce.UserLogin();
        userResponce.setId((user.getId()));
        userResponce.setUserName(user.getEmail());
        userResponce.setFullName(user.getName());
        String accessToken = this.securityUtil.createToken(user.getEmail(),user);
        LoginResponce loginResponce = new LoginResponce();
        loginResponce.setUser(userResponce);
        loginResponce.setAccessToken(accessToken);

        String refreshToken = this.securityUtil.createRefreshToken(user.getEmail(), user);
        user.setRefreshToken(refreshToken);
        this.userService.updateUser(user);


        ResponseCookie springCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtil.jwtRefreshTokenExpiration)
                .build();
        loginResponce.setSpringCookie(springCookie);
        return loginResponce;
    }
    public SingupResponce handleSignup(SignupRequest request) {
        User dbUser =this.userService.getUserByUserName(request.getEmail());
        if(dbUser != null && dbUser.isEnabled()){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        
        User user = User.builder()
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getFirstName() + " " + request.getLastName())
            .email(request.getEmail())
            .role(Role.USER)
            .phoneNumber(request.getPhoneNumber())
            .gender(request.getGender())
            .enable(false)
            .build();
        if(dbUser != null){
             user.setId(dbUser.getId());
            }
        user = this.userService.saveUser(user);
        long verificationToken = this.userService.handleUserRegistrationWithEmail(user);
        this.emailServiceImpl.send(request.getEmail(), user.getName(),verificationToken);

        SingupResponce responce = SingupResponce.builder()
            .userName(user.getEmail())
            .fullName(user.getName())
            .role(user.getRole())
            .gender(user.getGender())
            .phoneNumber(user.getPhoneNumber())
            .enable(false)
            .build();

        return responce;
    }
    public SingupResponce handleEmailConfirmation(String verificationToken) {
        List<String> tokenList = Arrays.asList(verificationToken.split(","));
        long tokenNumber = Long.parseLong(tokenList.get(0));
        String email =  tokenList.get(1);
        ConfirmationToken confirmationToken = this.confirmationService.getConfirmationByTokenAndEmail(tokenNumber,email).orElseThrow(
            () -> new VerificationException(Common.INVALID_TOKEN)
        );
        if(confirmationToken.getConfirmedAt() != null){
            throw new VerificationException(Common.USER_CONFIRMED);
        }
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new VerificationException(Common.TOKEN_EXPIRED);
        }
        this.confirmationService.ConfirmToken(tokenNumber,email);
        User user = this.userService.getUserByID(confirmationToken.getUser().getId());
        user.setEnable(true);
        this.userService.saveUser(user);

        SingupResponce responce = SingupResponce.builder()
        .userName(user.getEmail())
        .fullName(user.getName())
        .gender(user.getGender())
        .phoneNumber(user.getPhoneNumber())
        .enable(true)
        .build();

        return responce;
    }
    
}
