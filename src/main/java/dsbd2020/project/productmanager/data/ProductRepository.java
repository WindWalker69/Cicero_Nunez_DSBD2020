package dsbd2020.project.productmanager.data;

import dsbd2020.project.productmanager.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    Product findByModel(String productModel);
}
