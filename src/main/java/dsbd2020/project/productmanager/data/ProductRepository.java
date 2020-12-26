package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


//MongoRepository<Product, Integer>
public interface ProductRepository extends MongoRepository<Product, Integer> {


}
