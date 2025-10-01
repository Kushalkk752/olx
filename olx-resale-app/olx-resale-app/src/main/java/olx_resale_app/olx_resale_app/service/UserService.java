package olx_resale_app.olx_resale_app.service;

import olx_resale_app.olx_resale_app.entity.User;
import olx_resale_app.olx_resale_app.entity.enums.Role;
import olx_resale_app.olx_resale_app.payload.RegistrationRequest;
import olx_resale_app.olx_resale_app.payload.RegistrationResponse;

import java.util.Set;

public interface UserService {
    RegistrationResponse signUp(RegistrationRequest registrationRequest, Set<Role> role);

    User getUserById(Long userId);
}
