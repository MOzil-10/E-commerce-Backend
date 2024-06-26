package EcommerceBackend.Ecommerce.Service.Auth;

import EcommerceBackend.Ecommerce.Entity.Order;
import EcommerceBackend.Ecommerce.Entity.OrderStatus;
import EcommerceBackend.Ecommerce.Entity.User;
import EcommerceBackend.Ecommerce.Enums.UserRole;
import EcommerceBackend.Ecommerce.Repository.OrderRepository;
import EcommerceBackend.Ecommerce.Repository.UserRepository;
import EcommerceBackend.Ecommerce.dto.SignUpRequest;
import EcommerceBackend.Ecommerce.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl provides authentication-related services such as user creation and admin setup.
 */
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;

    /**
     * Creates a new user with the provided sign-up request details.
     *
     * @param signUpRequest the sign-up request containing user details
     * @return the created user's data transfer object (UserDto)
     */
    @Override
    public UserDto createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setUserRole(signUpRequest.getUserRole() != null ? signUpRequest.getUserRole() : UserRole.CUSTOMER); // Set role based on request or default to CUSTOMER

        User createdUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setFullName(createdUser.getFullName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());

        return userDto;
    }


    /**
     * Checks if a user with the given email already exists.
     *
     * @param email the email to check for existence
     * @return true if a user with the given email exists, false otherwise
     */
    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Creates an admin account if it does not already exist.
     * This method is called after the bean's properties have been set.
     */
    @PostConstruct
    public void createAdmin() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setFullName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }
}
