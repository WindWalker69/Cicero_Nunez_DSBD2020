package dsbd2020.project.productmanager.controller;

import dsbd2020.project.productmanager.entities.Category;
import dsbd2020.project.productmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = "/categories")
    public @ResponseBody Category addCategory(@Valid @RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping(path = "/categories/{id}")
    public @ResponseBody Category updateCategory(@PathVariable Integer id, @RequestBody Category categoryDto){
        return categoryService.updateCategory(id, categoryDto);
    }

    @GetMapping(path = "/categories/{id}")
    public @ResponseBody Category getCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

    @GetMapping(path = "/categories")
    public @ResponseBody ResponseEntity<Map<String,Object>> getAllCategories(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer per_page){

        return categoryService.getAllCategories(page, per_page);
    }
}
