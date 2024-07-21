package ru.javabegin.tasklist.backend_springboot.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backend_springboot.entity.Task;
import ru.javabegin.tasklist.backend_springboot.repo.TaskRepository;
import ru.javabegin.tasklist.backend_springboot.search.TaskSearchValues;
import ru.javabegin.tasklist.backend_springboot.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

// If an exception occurs, a 500 Internal Server Error code will be returned, so there is no need to wrap all actions in try-catch

// use @RestController instead of the regular @Controller, so all responses are immediately wrapped in JSON
// otherwise, extra work would be required, using @ResponseBody for the response, specifying the JSON response type
@RestController
@RequestMapping("/task") // base URL
public class TaskController {

    private final TaskRepository taskRepository; // service for accessing data (do not interact directly with repositories)


    // automatic injection of class instance through the constructor
    // do not use @Autowired for class variable, as "Field injection is not recommended"
    public TaskController(TaskRepository taskRepository, ConfigurableEnvironment environment) {
        this.taskRepository = taskRepository;
    }


    // retrieving all data
    @GetMapping("/all")
    public ResponseEntity<List<Task>> findAll() {

        MyLogger.showMethodName("Task: findAll()");

        return ResponseEntity.ok(taskRepository.findAll());
    }

    // adding
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        MyLogger.showMethodName("Task: add()");

        // checking for required parameters
        if (task.getId() != null && task.getId() != 0) {
            // id is automatically generated in the database (autoincrement), so it does not need to be passed, otherwise, a uniqueness conflict may occur
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty title value is passed
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskRepository.save(task)); // возвращаем созданный объект со сгенерированным id

    }


    // updating
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {

        MyLogger.showMethodName("Task: update()");

        // checking for required parameters
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // if an empty title value is passed
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save works for both adding and updating
        taskRepository.save(task);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }

    // deletion by id
    // the id parameter is passed not in the BODY of the request, but in the URL itself
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("task: delete()");

        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); //status 200
    }


    // retrieving object by id
    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {

        MyLogger.showMethodName("Task: findById()");

        Task task = null;

        try {
            task = taskRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {

        MyLogger.showMethodName("Task: search()");

        // avoid NullPointerException
        String text = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        // convert Boolean to Integer
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;

        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().isEmpty() || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // sorting object
        Sort sort = Sort.by(direction, sortColumn);

        //paging object
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page result = taskRepository.findByParams(text, completed, priorityId, categoryId, pageRequest);

        return ResponseEntity.ok(result);

    }


}
