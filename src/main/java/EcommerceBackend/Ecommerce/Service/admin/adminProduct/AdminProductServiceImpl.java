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

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        if (productDto.getImg() != null) {
            product.setImageData(productDto.getImg().getBytes());
        }

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getDto();
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
