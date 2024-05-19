package EcommerceBackend.Ecommerce.Service.Auth;

import EcommerceBackend.Ecommerce.dto.SignUpRequest;
import EcommerceBackend.Ecommerce.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignUpRequest signUpRequest);
    Boolean hasUserWithEmail(String email);
}
