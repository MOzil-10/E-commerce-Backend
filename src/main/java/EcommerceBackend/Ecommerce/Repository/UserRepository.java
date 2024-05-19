package EcommerceBackend.Ecommerce.Repository;

import EcommerceBackend.Ecommerce.Entity.User;
import EcommerceBackend.Ecommerce.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides methods for accessing User data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their email.
     *
     * @param email the email of the user to find
     * @return an Optional containing the found user, or empty if no user is found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a User by their role.
     *
     * @param userRole the role of the user to find
     * @return the found user with the specified role
     */
    User findByUserRole(UserRole userRole);
}
