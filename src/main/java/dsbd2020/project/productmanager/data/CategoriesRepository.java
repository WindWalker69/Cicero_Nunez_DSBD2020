package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesRepository extends MongoRepository<Categories, Integer> {
}
