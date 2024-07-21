package ru.javabegin.tasklist.backend_springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.tasklist.backend_springboot.entity.Priority;
import ru.javabegin.tasklist.backend_springboot.repo.PriorityRepository;

import java.util.List;

@Service
@Transactional
public class PriorityService {

    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Priority add(Priority priority) {
        return repository.save(priority);
    }

    public Priority update(Priority priority) {
        return repository.save(priority);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Priority findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Priority> findByTitle(String text) {
        return repository.findByTitle(text);
    }

}
