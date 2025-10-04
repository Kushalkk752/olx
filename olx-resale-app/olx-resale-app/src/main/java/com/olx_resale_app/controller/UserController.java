package com.olx_resale_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.olx_resale_app.payload.RegistrationRequest;
import com.olx_resale_app.payload.RegistrationResponse;
import com.olx_resale_app.payload.UserDto;
import com.olx_resale_app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.olx_resale_app.entity.enums.Role;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "2 User Controller")
@RequestMapping("/api/user")
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @Operation(summary = "Register a new user", description = "This end point allows users to sign up with specified role")
    @PostMapping("/signUp")
    public ResponseEntity<RegistrationResponse> signUp(
            @Validated @RequestBody RegistrationRequest registrationRequest,
            @RequestParam("role") Set<Role> role) {
        RegistrationResponse registrationResponse = userService.signUp(registrationRequest, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @Operation(summary = "Welcome message", description = "This end point returns a welcome message" +
            "Accessible by SELLER, ADMIN and GUEST roles")
    @GetMapping("/welcome")
    @PreAuthorize("hasAnyRole('SELLER', 'BUYER', 'ADMIN', 'GUEST')")
    public ResponseEntity<Map<String, String>> welcome(
            @Parameter(description = "Optional header to indicate Guest role")
            @RequestHeader(value = "X-Role-Guest", required = false) String guestHeader) {
        log.info("Welcome endpoint accessed. Guest header: {}", guestHeader);
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Welcome to OLX Resale App");
        log.info("Returning welcome message");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "Show the current user details")
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfileDetails() {
        log.info("fetching profile details from authenticated user. ");
        UserDto userDto = userService.getProfileDetails();
        log.info("profile details successfully retrieved for the user: {}", userDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
