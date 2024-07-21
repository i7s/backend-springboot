package ru.javabegin.tasklist.backend_springboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backend_springboot.entity.Priority;
import ru.javabegin.tasklist.backend_springboot.repo.PriorityRepository;
import ru.javabegin.tasklist.backend_springboot.search.PrioritySearchValues;
import ru.javabegin.tasklist.backend_springboot.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    private PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/all")
    public List<Priority> findAll() {
        MyLogger.showMethodName("PriorityController: findAll()");

        return priorityRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        MyLogger.showMethodName("PriorityController: add()");

        // checking for mandatory parameters
        if (priority.getId() != null && priority.getId() != 0) {
            // The id is automatically generated in the database (autoincrement), so it should not be created, otherwise, there may be a conflict with the unique value.
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty title value is passed
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty color value is passed
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority) {
        MyLogger.showMethodName("PriorityController: update()");

        // check for required parameters
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty title value is passed
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty color value is passed
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        // save works for both adding and updating
        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {
        MyLogger.showMethodName("PriorityController: findById()");
        Priority priority = null;

        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id:" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        MyLogger.showMethodName("PriorityController: deleteById()");

        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id:" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        MyLogger.showMethodName("PriorityController: search()");
        // if the text is empty or null, all categories will be returned
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }

}
