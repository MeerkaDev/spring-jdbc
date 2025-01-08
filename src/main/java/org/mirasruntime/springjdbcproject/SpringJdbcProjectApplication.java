package org.mirasruntime.springjdbcproject;

import org.mirasruntime.springjdbcproject.model.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJdbcProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcProjectApplication.class, args);
        Category category = new Category();
        category.getId();
    }

}
