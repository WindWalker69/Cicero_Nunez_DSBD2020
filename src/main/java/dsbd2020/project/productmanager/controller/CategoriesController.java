package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.entities.Categories;
import dsbd2020.project.productmanager.support.NextSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public @ResponseBody Iterable<Categories> getAllCategories(){
        return categoriesRepository.findAll(); //temporaneo
    }
}
