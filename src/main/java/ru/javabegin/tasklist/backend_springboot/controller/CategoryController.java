package ru.javabegin.tasklist.backend_springboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backend_springboot.entity.Category;
import ru.javabegin.tasklist.backend_springboot.repo.CategoryRepository;
import ru.javabegin.tasklist.backend_springboot.search.CategorySearchValues;
import ru.javabegin.tasklist.backend_springboot.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

// We use @RestController instead of the regular @Controller so that all responses are automatically wrapped in JSON
// Otherwise, we would have to do extra work, use @ResponseBody for the response, and specify the JSON response type
@RestController
@RequestMapping("/category") // base address
public class CategoryController {

    // access data from the db
    private CategoryRepository categoryRepository;

    // automatic injection of a class instance through the constructor
    // do not use @Autowired for class variables because "Field injection is not recommended"
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        MyLogger.showMethodName("CategoryController: findAll()");

        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        MyLogger.showMethodName("CategoryController: add()");

        // checking for mandatory parameters
        if (category.getId() != null && category.getId() != 0) {
            // The id is automatically generated in the database (autoincrement), so it should not be created, otherwise, there may be a conflict with the unique value.
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty title value is passed
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        MyLogger.showMethodName("CategoryController: update()");

        if (category.getId() == null && category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null && category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        MyLogger.showMethodName("CategoryController: findById()");
        Category category = null;

        try {
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id:" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController: deleteById()");

        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id:" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // search by any parameters in CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {
        MyLogger.showMethodName("CategoryController: search()");

        // if the text is empty or null, all categories will be returned
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getText()));
    }


}
