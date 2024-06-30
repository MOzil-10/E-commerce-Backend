package EcommerceBackend.Ecommerce.Controller.admin;

import EcommerceBackend.Ecommerce.Service.admin.adminProduct.AdminProductService;
import EcommerceBackend.Ecommerce.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("description") String description,
            @RequestParam("img") MultipartFile img) throws IOException {

        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(categoryId);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setImg(img);

        ProductDto savedProduct = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = adminProductService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<ByteArrayResource> getProductImage(@PathVariable Long id) {
        try {
            byte[] imageData = adminProductService.getProductImage(id);
            if (imageData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            ByteArrayResource resource = new ByteArrayResource(imageData);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(Long productId) {
        boolean isDeleted = adminProductService.deleteProduct(productId);

        if(isDeleted) {
            return ResponseEntity.noContent().build();
        }

        else {
            return ResponseEntity.notFound().build();
        }
    }
}
