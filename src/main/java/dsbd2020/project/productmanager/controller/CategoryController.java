package dsbd2020.project.productmanager.controller;

import com.google.gson.Gson;
import dsbd2020.project.productmanager.entities.Category;
import dsbd2020.project.productmanager.message.HttpErrorMessage;
import dsbd2020.project.productmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicLogging}")
    private String topicName;

    public void sendMessage(String msg){
        kafkaTemplate.send(topicName, msg);
    }

    @PostMapping(path = "/categories")
    public @ResponseBody Category addCategory(@RequestBody Category category){
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

        /*HttpErrorMessage errMsg = new HttpErrorMessage()
                .setTimestamp("ciao")
                .setSourceIp("ciao")
                .setService("ciao")
                .setRequest("ciao")
                .setError("ciao");
        sendMessage(new Gson().toJson(errMsg));*/

        return categoryService.getAllCategories(page, per_page);
    }
}
