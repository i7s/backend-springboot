package ru.javabegin.tasklist.backend_springboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backend_springboot.entity.Stat;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {

}
