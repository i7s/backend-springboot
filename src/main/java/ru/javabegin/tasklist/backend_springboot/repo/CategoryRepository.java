package ru.javabegin.tasklist.backend_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backend_springboot.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //get all values, sort by method name
    List<Category> findAllByOrderByTitleAsc();
}
