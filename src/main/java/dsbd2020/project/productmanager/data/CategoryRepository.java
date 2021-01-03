package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends MongoRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {
    Category findByCategory(String categoryName);
}
