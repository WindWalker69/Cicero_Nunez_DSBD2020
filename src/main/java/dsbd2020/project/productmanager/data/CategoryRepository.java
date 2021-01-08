package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Integer> {
    Category findByCategory(String categoryName);
}
