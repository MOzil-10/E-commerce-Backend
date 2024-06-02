package EcommerceBackend.Ecommerce.Service.admin.category;

import EcommerceBackend.Ecommerce.Entity.Category;
import EcommerceBackend.Ecommerce.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
