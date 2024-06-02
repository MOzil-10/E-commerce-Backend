package EcommerceBackend.Ecommerce.Service.admin.category;

import EcommerceBackend.Ecommerce.Entity.Category;
import EcommerceBackend.Ecommerce.dto.CategoryDto;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
