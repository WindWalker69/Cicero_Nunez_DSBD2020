package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepository extends CrudRepository<Integer, Categories> {
}
