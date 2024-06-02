package EcommerceBackend.Ecommerce.Repository;

import EcommerceBackend.Ecommerce.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
