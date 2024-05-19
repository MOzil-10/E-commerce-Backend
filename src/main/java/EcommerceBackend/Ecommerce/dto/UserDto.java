package EcommerceBackend.Ecommerce.dto;

import EcommerceBackend.Ecommerce.Enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private UserRole userRole;
}
