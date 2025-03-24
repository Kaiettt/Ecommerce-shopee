package com.ak.ecommerce_vender.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.ConfirmationToken;
import com.ak.ecommerce_vender.domain.entity.User;
import com.ak.ecommerce_vender.domain.request.UserCreateRequest;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.ConfirmationTokenRepository;
import com.ak.ecommerce_vender.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User createNewUser(UserCreateRequest request){
        User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .birthDate(request.getBirthDate())
        .phoneNumber(request.getPhoneNumber())
        .gender(request.getGender())
        .address(request.getAddress())
        .role(request.getRole())
        .enable(true)
        .build();
        return this.userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    public User getUserByUserName(String username) {
        return this.userRepository.findByEmail(username)
            .orElseThrow(() -> new EntityNotExistException(Common.USER_NOT_FOUND));
    }
    public User getUserByID(long id){
        return this.userRepository.findById(id)
        .orElseThrow(() -> new EntityNotExistException(Common.USER_NOT_FOUND));
    }
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }
    public User getUserByRefreshToken(String refresh_token) {
        return this.userRepository.findByRefreshToken(refresh_token)
        .orElseThrow(() -> new EntityNotExistException(Common.REFRESH_TOKEN_NOT_FOUND));
    }
    public User saveUser(User user){
        return this.userRepository.save(user);
    }
    public long handleUserRegistrationWithEmail(User user) {
       Random random = new Random();
        long token = 100000 + random.nextInt(900000);
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
            .token(token)
            .user(user)
            .expiresAt(LocalDateTime.now().plusMinutes(30)) // Set expiresAt to 30 minutes
            .build();
        this.confirmationTokenRepository.save(confirmationToken);
        return token;
    }
    public User getUserById(long id) {
        return this.userRepository.findById(id)
        .orElseThrow(() -> new EntityNotExistException(Common.USER_NOT_FOUND));
    }
    public void deleteUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotExistException(Common.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
    
}
