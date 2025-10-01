package olx_resale_app.olx_resale_app.repository;

import olx_resale_app.olx_resale_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrMobileNumber(String email, String mobileNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByMobileNumber(String mobileNumber);
}
