package com.olx_resale_app.service;

import com.olx_resale_app.entity.AppUser;

public interface JwtService {
    String generateToken(AppUser user);
    Long getUserIdFromToken(String token);
}
