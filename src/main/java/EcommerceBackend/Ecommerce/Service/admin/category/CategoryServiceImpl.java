package EcommerceBackend.Ecommerce.Service.admin.category;

import EcommerceBackend.Ecommerce.Entity.Category;
import EcommerceBackend.Ecommerce.Repository.CategoryRepository;
import EcommerceBackend.Ecommerce.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
