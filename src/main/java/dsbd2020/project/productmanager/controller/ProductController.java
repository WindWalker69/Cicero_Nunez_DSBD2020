package dsbd2020.project.productmanager.controller;

import com.google.gson.Gson;
import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Categories;
import dsbd2020.project.productmanager.support.*;
import dsbd2020.project.productmanager.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
            productrequest.setId(nextSequenceService.getNextSequence(Product.SEQUENCE_NAME));
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
    public @ResponseBody ResponseEntity<Map<String,Object>> getAllProduct(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer per_page) {

        try{
            if(per_page <= 0 || page <= 0)
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

            int first_id = per_page * (page - 1);
            List<Integer> ids = new ArrayList<>();
            for(int i = 0; i <= per_page; i++)
                ids.add(first_id + i);

            List<Product> products = new ArrayList<>((Collection<? extends Product>) productRepository.findAllById(ids));

            if(products.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            Pageable paging = PageRequest.of(page, per_page);
            Page<Product> pageProduct = productRepository.findAll(paging);

            Map<String,Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", pageProduct.getNumber());
            response.put("totalProducts", pageProduct.getTotalElements());
            response.put("totalPages", pageProduct.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public @ResponseBody Ping_ack_response getPing_ack(){
        Ping_ack_response ping_ack_response = new Ping_ack_response().setDbStatus("up").setServiceStatus("up");
        return ping_ack_response;
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public @ResponseBody TopicOrderCompleted productUpdateRequest( @RequestBody TopicOrderCompleted productrequest) {
        sendMessage(new Gson().toJson(productrequest), "orderupdates");
        return productrequest;
    }
}