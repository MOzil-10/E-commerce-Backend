package EcommerceBackend.Ecommerce.Service.admin.adminProduct;

import EcommerceBackend.Ecommerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String name);
}
