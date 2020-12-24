package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.data.CategoriesRepository;
import dsbd2020.project.productmanager.entities.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @PostMapping(path = "/categories")
    public @ResponseBody Categories addCategory(@RequestBody Categories category){
        return categoriesRepository.save(category);
    }

    @PutMapping(path = "/categories/{id}")
    public @ResponseBody Categories updateCategory(@PathVariable Integer id, @RequestBody Categories categoryDto){
        Optional<Categories> category = categoriesRepository.findById(id);
        if()
    }
}
