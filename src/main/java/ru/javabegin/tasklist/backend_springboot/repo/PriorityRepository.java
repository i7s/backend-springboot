package ru.javabegin.tasklist.backend_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backend_springboot.entity.Priority;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // if title == null or == '', then we will get all values
    @Query("SELECT c FROM Priority c where " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title,'%')))  " +
            "order by c.title asc")
    List<Priority> findByTitle(@Param("title")String title);

    List<Priority> findAllByOrderByIdAsc();
}
