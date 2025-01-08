package org.mirasruntime.springjdbcproject.controller;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.springjdbcproject.dao.ProductDao;
import org.mirasruntime.springjdbcproject.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductDao productDao;

    @GetMapping
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productDao.create(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return productDao.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productDao.deleteById(id);
    }
}
