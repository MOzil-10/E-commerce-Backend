package EcommerceBackend.Ecommerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteImage;
    private Long categoryId;
    private MultipartFile img;
}
