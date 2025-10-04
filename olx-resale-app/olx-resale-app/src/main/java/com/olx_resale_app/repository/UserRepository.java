package com.olx_resale_app.repository;

import com.olx_resale_app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailOrMobileNumber(String email, String mobileNumber);
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByMobileNumber(String mobileNumber);
}
