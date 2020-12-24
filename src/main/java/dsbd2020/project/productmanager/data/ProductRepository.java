package dsbd2020.project.productmanager.data;


import dsbd2020.project.productmanager.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
