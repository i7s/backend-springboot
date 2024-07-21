package ru.javabegin.tasklist.backend_springboot.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
// possible values to search for categories
public class CategorySearchValues {

    private String text;

}
