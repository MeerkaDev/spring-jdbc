package org.mirasruntime.springjdbcproject.dao;

import org.mirasruntime.springjdbcproject.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findById(int id);

    Category create(Category category);

    Category update(Category category);

    void deleteById(int id);
}
