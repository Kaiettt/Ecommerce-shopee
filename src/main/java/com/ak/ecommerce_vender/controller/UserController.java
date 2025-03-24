package com.ak.ecommerce_vender.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.User;
import com.ak.ecommerce_vender.domain.request.UserCreateRequest;
import com.ak.ecommerce_vender.error.CheckRequest;
import com.ak.ecommerce_vender.error.InvalidIdException;
import com.ak.ecommerce_vender.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createNewUser(request));
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        if(id == null && !CheckRequest.isNumeric(id)){
            throw new InvalidIdException(Common.INVALID_ID);
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(Long.parseLong(id)));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        if(id == null && !CheckRequest.isNumeric(id)){
            throw new InvalidIdException(Common.INVALID_ID);
        }
        this.userService.deleteUserById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
