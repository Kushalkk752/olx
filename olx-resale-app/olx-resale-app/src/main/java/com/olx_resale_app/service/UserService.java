package com.olx_resale_app.service;

import com.olx_resale_app.entity.AppUser;
import com.olx_resale_app.entity.enums.Role;
import com.olx_resale_app.payload.RegistrationRequest;
import com.olx_resale_app.payload.RegistrationResponse;
import com.olx_resale_app.payload.UserDto;

import java.util.Set;

public interface UserService {
    RegistrationResponse signUp(RegistrationRequest registrationRequest, Set<Role> role);

    AppUser getUserById(Long userId);

    UserDto getProfileDetails();
}
