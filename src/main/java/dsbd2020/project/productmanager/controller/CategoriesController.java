package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.entities.Categories;
import dsbd2020.project.productmanager.support.NextSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    @PostMapping(path = "/categories")
    public @ResponseBody Categories addCategory(@RequestBody Categories category){
        category.setId(nextSequenceService.getNextSequence(Categories.SEQUENCE_NAME));
        return categoriesRepository.save(category);
    }

    @PutMapping(path = "/categories/{id}")
    public @ResponseBody Categories updateCategory(@PathVariable Integer id, @RequestBody Categories categoryDto){
        Optional<Categories> categoryToUpdate = categoriesRepository.findById(id);
        if(categoryToUpdate.isPresent())
            categoryToUpdate.get().setName(categoryDto.getName());
        return categoriesRepository.save(categoryToUpdate.get());
    }

    @GetMapping(path = "/categories/{id}")
    public @ResponseBody Optional<Categories> getCategory(@PathVariable Integer id){
        return categoriesRepository.findById(id);
    }

    @GetMapping(path = "/categories")
    public @ResponseBody ResponseEntity<Map<String,Object>> getAllCategories(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer per_page){

        try{
            if(per_page <= 0 || page <= 0)
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

            List<Integer> ids = new ArrayList<>();
            int first_id = per_page * (page - 1);
            for(int i = 0; i <= per_page; i++)
                ids.add(first_id + i);

            List<Categories> categories = new ArrayList<>((Collection<? extends Categories>) categoriesRepository.findAllById(ids));

            if(categories.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            Pageable paging = PageRequest.of(page, per_page);
            Page<Categories> pageCategories = categoriesRepository.findAll(paging);

            Map<String,Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("currentPage", pageCategories.getNumber());
            response.put("totalCategories", pageCategories.getTotalElements());
            response.put("totalPages", pageCategories.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
