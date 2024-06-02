package EcommerceBackend.Ecommerce.Service.admin.adminProduct;

import EcommerceBackend.Ecommerce.Entity.Category;
import EcommerceBackend.Ecommerce.Entity.Product;
import EcommerceBackend.Ecommerce.Repository.CategoryRepository;
import EcommerceBackend.Ecommerce.Repository.ProductRepository;
import EcommerceBackend.Ecommerce.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        // Validate productDto here if needed

        // Create a new product entity
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        // Check if the image is not null before calling getBytes
        if (productDto.getImg() != null) {
            product.setImageData(productDto.getImg().getBytes());
        }

        // Fetch the category from the repository based on categoryId
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Set the category for the product
        product.setCategory(category);

        // Save the product entity to the database
        Product savedProduct = productRepository.save(product);

        // Convert the saved product entity to DTO and return
        return savedProduct.getDto();
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
