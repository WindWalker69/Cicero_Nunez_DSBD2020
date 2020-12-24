package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Categories;
import dsbd2020.project.productmanager.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    CategoriesRepository categoriesRepository;

    //POST /products
    @PostMapping(path = "/products")
    public @ResponseBody
    String addProduct(@RequestBody Product product) {

        if(categoriesRepository.existsById( product.getCategories().getId())==true)
        {
            productRepository.save(product);
            return  "Done";
        }
        return "Errore not Add Product: Not found Categories";
    }

    //PUT /products/{id}
    @PutMapping(path = "/products/{id}")
    public @ResponseBody String updateProduct(@PathVariable Integer id, @RequestBody Product productDto) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if (productToUpdate.isPresent()) {
            Product p = productToUpdate.get();
            p.setBrand(productDto.getBrand());
            p.setCategories(productDto.getCategories());
            p.setDescription(productDto.getDescription());
            p.setPrice(productDto.getPrice());
            p.setModel(productDto.getModel());
            p.setQuantity(productDto.getQuantity());
            productRepository.save(p);
            return "Done";
        }
        else return "Errore";
    }

    //GET /products/
    @GetMapping(path = "/products")
    public @ResponseBody
    Iterable<Product> getAll() {
        return productRepository.findAll();
    }

}