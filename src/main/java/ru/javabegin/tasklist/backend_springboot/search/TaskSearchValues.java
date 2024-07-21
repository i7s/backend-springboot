package ru.javabegin.tasklist.backend_springboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchValues {

    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

    //paging
    private Integer pageNumber;
    private Integer pageSize;

    //sort
    private String sortColumn;
    private String SortDirection;

}
