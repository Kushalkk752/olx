package olx_resale_app.olx_resale_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import olx_resale_app.olx_resale_app.payload.RegistrationRequest;
import olx_resale_app.olx_resale_app.payload.RegistrationResponse;
import olx_resale_app.olx_resale_app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import olx_resale_app.olx_resale_app.entity.enums.Role;
import java.util.*;

@Tag(name = "2 User Controller")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @Operation(summary = "Register a new user", description = "This end point allows users to sign up with specified role")
    @PostMapping("/signUp")
    public ResponseEntity<RegistrationResponse> signUp(
            @Validated @RequestBody RegistrationRequest registrationRequest,
            @RequestParam("role") Set<Role> role){
        RegistrationResponse registrationResponse = userService.signUp(registrationRequest, role);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> welcome(
            @Parameter(description = "Optional header to indicate Guest role")
            @RequestHeader(value = "X-Role-Guest", required = false) String guestHeader) {

        log.info("Welcome endpoint accessed. Guest header: {}", guestHeader);

        Map<String, String> response = new HashMap<>();
        response.put("Message", "Welcome to OLX Resale App");

        log.info("Returning welcome message");
        return ResponseEntity.ok(response);
    }

}
