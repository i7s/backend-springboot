package ru.javabegin.tasklist.backend_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backend_springboot.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // the parameter can be null or empty
    @Query("SELECT p FROM Task p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:priorityId is null or p.priority.id=:priorityId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    List<Task> findByParams(@Param("title") String title, @Param("completed") Integer completed, @Param("priorityId") Long priorityId, @Param("categoryId") Long categoryId);
}
