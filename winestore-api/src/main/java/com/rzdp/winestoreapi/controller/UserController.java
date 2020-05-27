package com.rzdp.winestoreapi.controller;

import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping("/v1/users/{id}/photo")
    public ResponseEntity<MessageResponse> updateUserPhoto(@PathVariable("id") long userId,
                                                           @RequestParam("photo") MultipartFile file) {
        return new ResponseEntity<>(userService.updateUserPhoto(userId, file), HttpStatus.OK);
    }
}
