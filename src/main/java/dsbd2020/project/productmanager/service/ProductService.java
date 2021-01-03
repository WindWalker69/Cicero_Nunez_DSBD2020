package dsbd2020.project.productmanager.service;

import dsbd2020.project.productmanager.controller.ProductRequest;
import dsbd2020.project.productmanager.data.CategoryRepository;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Category;
import dsbd2020.project.productmanager.entities.Product;
import dsbd2020.project.productmanager.exception.NoDataFoundException;
import dsbd2020.project.productmanager.exception.ProductNotFoundException;
import dsbd2020.project.productmanager.exception.WrongRequestBodyException;
import dsbd2020.project.productmanager.exception.WrongRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    //POST
    public Product addProduct(ProductRequest productRequest){
        Category category = categoryRepository.findByCategory(productRequest.getCategory());

        if(categoryRepository.findByCategory(productRequest.getCategory()) != null)
            if(productRepository.findByModel(productRequest.getModel()) == null){
                Product product = new Product()
                        .setId(nextSequenceService.getNextSequence(Product.SEQUENCE_NAME))
                        .setBrand(productRequest.getBrand())
                        .setModel(productRequest.getModel())
                        .setCategory(category)
                        .setDescription(productRequest.getDescription())
                        .setPrice(productRequest.getPrice())
                        .setQuantity(productRequest.getQuantity());
                return productRepository.save(product);
            }
            else
                throw new WrongRequestBodyException("Product model already exists");
        else
            throw new WrongRequestBodyException("Category name not exists");
    }

    //PUT
    public Product updateProduct(Integer id, ProductRequest productRequest){
        Optional<Product> productToUpdate = productRepository.findById(id);

        if(productToUpdate.isPresent()){
            Category category = categoryRepository.findByCategory(
                    (String) getRightParam(productRequest.getCategory(), productToUpdate.get().getCategory().getCategory()));
            Product alreadyExistingProduct = productRepository.findByModel(
                    (String) getRightParam(productRequest.getModel(), productToUpdate.get().getModel()));

            if(category != null)
                if(alreadyExistingProduct == null){
                    productToUpdate.get()
                            .setBrand((String) getRightParam(productRequest.getBrand(), productToUpdate.get().getBrand()))
                            .setModel((String) getRightParam(productRequest.getModel(), productToUpdate.get().getModel()))
                            .setCategory(category)
                            .setDescription((String) getRightParam(productRequest.getDescription(), productToUpdate.get().getDescription()))
                            .setPrice((Double) getRightParam(productRequest.getPrice(), productToUpdate.get().getPrice()))
                            .setQuantity((Integer) getRightParam(productRequest.getQuantity(), productToUpdate.get().getQuantity()));
                    return productRepository.save(productToUpdate.get());
                }
                else
                    throw new WrongRequestBodyException("Product model already exists");
            else
                throw new WrongRequestBodyException("Category name not exists");
        }
        else
            throw new ProductNotFoundException(id);
    }

    //GET
    public Product getProduct(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //GET
    public ResponseEntity<Map<String, Object>> getAllProducts(Integer page, Integer per_page){

        if(per_page <= 0 || page <= 0)
            throw new WrongRequestParameterException();

        List<Integer> ids = new LinkedList<>();
        int first_id = per_page * (page - 1);
        for(int i = 0; i <= per_page; i++)
            ids.add(first_id + i);

        List<Product> products = (List<Product>) productRepository.findAllById(ids);

        if(products.isEmpty())
            throw new NoDataFoundException();

        Pageable paging = PageRequest.of(page, per_page);
        Page<Product> pageProducts = productRepository.findAll(paging);

        Map<String,Object> response = new HashMap<>();
        response.put("products", products);
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalCategories", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //METODI PRIVATI
    private Object getRightParam(Object doubtfulParam, Object certainParam){
        if(doubtfulParam == null)
            return certainParam;
        else
            return doubtfulParam;
    }
}