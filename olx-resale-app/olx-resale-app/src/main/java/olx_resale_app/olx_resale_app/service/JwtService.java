package olx_resale_app.olx_resale_app.service;

import olx_resale_app.olx_resale_app.entity.User;

public interface JwtService {
    String generateToken(User user);
    Long getUserIdFromToken(String token);
}
