package ru.javabegin.tasklist.backend_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backend_springboot.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

}