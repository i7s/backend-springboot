package ru.javabegin.tasklist.backend_springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.tasklist.backend_springboot.entity.Stat;
import ru.javabegin.tasklist.backend_springboot.repo.StatRepository;

@Service
@Transactional
public class StatService {

    private final StatRepository repository;

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findById(Long id){
        return repository.findById(id).get();
    }

}
