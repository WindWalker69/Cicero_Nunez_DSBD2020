package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Categories;
import dsbd2020.project.productmanager.entities.Product;
import dsbd2020.project.productmanager.entities.ProductRequest;
import dsbd2020.project.productmanager.support.NextSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public @ResponseBody
    Product addProduct(@Valid @RequestBody ProductRequest productrequest) {

        if(categoriesRepository.existsById(productrequest.getCategory()))
        {
            Categories categories= categoriesRepository.findById(productrequest.getCategory()).get();
            productrequest.setId(nextSequenceService.getNextSequence("database_sequences"));
            Product product = new Product().setProductRequest(productrequest, categories);
            return productRepository.save(product);
        }
        return null;
    }

    //PUT /products/{id}
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public @ResponseBody Product updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductRequest productDto) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if (productToUpdate.isPresent() && categoriesRepository.existsById(productDto.getCategory())) {
            productToUpdate.get().setBrand(productDto.getBrand());
            productToUpdate.get().setCategory(categoriesRepository.findById(productDto.getCategory()).get());
            productToUpdate.get().setDescription(productDto.getDescription());
            productToUpdate.get().setPrice(productDto.getPrice());
            productToUpdate.get().setModel(productDto.getModel());
            productToUpdate.get().setQuantity(productDto.getQuantity());
            return productRepository.save(productToUpdate.get());
        }
        else return null;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Product> getProduct(@PathVariable Integer id){
        return productRepository.findById(id);
    }

    //GET /products/
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Product> getAll() {
        return productRepository.findAll();
    }

}