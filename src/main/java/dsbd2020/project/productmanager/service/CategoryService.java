package dsbd2020.project.productmanager.service;

import dsbd2020.project.productmanager.data.CategoryRepository;
import dsbd2020.project.productmanager.entities.Category;
import dsbd2020.project.productmanager.exception.CategoryNotFoundException;
import dsbd2020.project.productmanager.exception.NoDataFoundException;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    //POST
    public Category addCategory(Category category){
        if(categoryRepository.findByCategory(category.getCategory()) != null)
            throw new WrongRequestBodyException("Category name already exists");

        category.setId(nextSequenceService.getNextSequence(Category.SEQUENCE_NAME));
        return categoryRepository.save(category);
    }

    //PUT
    public Category updateCategory(Integer id, Category categoryDto){
        Optional<Category> categoryToUpdate = categoryRepository.findById(id);

        if(categoryToUpdate.isPresent())

            if(categoryDto.getCategory() != null){
                if(categoryRepository.findByCategory(categoryDto.getCategory()) != null)
                        throw new WrongRequestBodyException("Category name already exists");

                /*Iterable<Category> categories = categoryRepository.findAll();
                for (Category c : categories)
                    if (c.getCategory().equalsIgnoreCase(categoryDto.getCategory()))
                        throw new WrongRequestBodyException("Category name already exists");*/

                categoryToUpdate.get().setCategory(categoryDto.getCategory());
                return categoryRepository.save(categoryToUpdate.get());
            }
            else
                throw new WrongRequestBodyException("Insert only the category name");
        else
            throw new CategoryNotFoundException(id);
    }

    //GET
    public Category getCategory(Integer id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    //GET
    public ResponseEntity<Map<String, Object>> getAllCategories(Integer page, Integer per_page){

        if(per_page <= 0 || page <= 0)
            throw new WrongRequestParameterException();

        List<Integer> ids = new LinkedList<>();
        int first_id = per_page * (page - 1);
        for(int i = 0; i <= per_page; i++)
            ids.add(first_id + i);

        List<Category> categories = (List<Category>) categoryRepository.findAllById(ids);
        if(categories.isEmpty())
            throw new NoDataFoundException();

        Pageable paging = PageRequest.of(page, per_page);
        Page<Category> pageCategories = categoryRepository.findAll(paging);

        Map<String,Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("currentPage", pageCategories.getNumber());
        response.put("totalCategories", pageCategories.getTotalElements());
        response.put("totalPages", pageCategories.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
