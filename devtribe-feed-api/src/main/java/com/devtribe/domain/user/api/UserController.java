package com.devtribe.domain.user.api;

import com.devtribe.domain.user.application.UserService;
import com.devtribe.domain.user.application.dtos.CreateUserRequest;
import com.devtribe.domain.user.application.dtos.CreateUserResponse;
import com.devtribe.domain.user.application.dtos.UserProfileResponse;
import com.devtribe.domain.user.application.dtos.UserProfileUpdateRequest;
import com.devtribe.global.security.CustomUserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/profile")
    public void updateUserProfile(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @RequestBody UserProfileUpdateRequest request
    ) {
        userService.updateUserProfile(userDetail.getUserId(), request);
    }

    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return userService.getUserProfile(userDetail.getUserId());
    }
}
