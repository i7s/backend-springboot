package ru.javabegin.tasklist.backend_springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.tasklist.backend_springboot.entity.Category;
import ru.javabegin.tasklist.backend_springboot.repo.CategoryRepository;

import java.util.List;

// Service class should be created for data access
// This approach is useful for future improvements and proper architecture (especially if working with transactions)
@Service

// all class methods must execute without error for the transaction to complete
// if an exception occurs in a method, all performed operations will be rolled back
@Transactional
public class CategoryService {

    private final CategoryRepository repository; // the service has the right to access the repository (database)

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category add(Category category) {
        return repository.save(category); // the save method updates or creates a new object if it did not exist
    }

    public Category update(Category category) {
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String text) {
        return repository.findByTitle(text);
    }

    public Category findById(Long id) {
        return repository.findById(id).get(); //the object needs to be retrieved using the get() method
    }

    public List<Category> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByTitleAsc();
    }

}
