package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public @ResponseBody
    String addProduct(@RequestBody Product product) {

        if(categoriesRepository.existsById(product.getCategory().getId()))
        {
            productRepository.save(product);
            return  "Done";
        }
        return "Errore not Add Product: Not found Categories";
    }

    //PUT /products/{id}
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public @ResponseBody String updateProduct(@PathVariable Integer id, @RequestBody Product productDto) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if (productToUpdate.isPresent()) {
            Product p = productToUpdate.get();
            p.setBrand(productDto.getBrand());
            p.setCategory(productDto.getCategory());
            p.setDescription(productDto.getDescription());
            p.setPrice(productDto.getPrice());
            p.setModel(productDto.getModel());
            p.setQuantity(productDto.getQuantity());
            productRepository.save(p);
            return "Done";
        }
        else return "Errore";
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