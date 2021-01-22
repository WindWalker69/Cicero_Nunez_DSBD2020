package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.entities.Product;
import dsbd2020.project.productmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/products")
    public @ResponseBody Product addProduct(@Valid @RequestBody ProductRequest product){
        return productService.addProduct(product);
    }

    @PutMapping(path = "/products/{id}")
    public @ResponseBody Product updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productDto){
        return productService.updateProduct(id, productDto);
    }

    @GetMapping(path = "/products/{id}")
    public @ResponseBody Product getProduct(@PathVariable Integer id){
        return productService.getProduct(id);
    }

    @GetMapping(path = "/products")
    public @ResponseBody ResponseEntity<Map<String,Object>> getAllProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer per_page){

        return productService.getAllProducts(page, per_page);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public @ResponseBody Ping_ack_response getPing_ack(){
        Ping_ack_response ping_ack_response = new Ping_ack_response().setDbStatus("up").setServiceStatus("up");
        return ping_ack_response;
    }
}