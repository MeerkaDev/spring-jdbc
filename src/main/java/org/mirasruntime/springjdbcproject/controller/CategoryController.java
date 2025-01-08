package org.mirasruntime.springjdbcproject.controller;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.springjdbcproject.dao.CategoryDao;
import org.mirasruntime.springjdbcproject.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDao categoryDao;

    @GetMapping
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
        return categoryDao.findById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryDao.create(category);
    }

    @PutMapping
    public Category update(@RequestBody Category category) {
        return categoryDao.update(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryDao.deleteById(id);
    }
}
