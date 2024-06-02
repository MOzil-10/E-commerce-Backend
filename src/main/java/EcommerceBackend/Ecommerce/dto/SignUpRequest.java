package EcommerceBackend.Ecommerce.dto;

import EcommerceBackend.Ecommerce.Enums.UserRole;
import lombok.Data;

@Data
public class SignUpRequest {

    private String fullName;
    private String email;
    private String password;
    private UserRole userRole;

}
