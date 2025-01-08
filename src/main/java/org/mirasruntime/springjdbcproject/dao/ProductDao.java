package org.mirasruntime.springjdbcproject.dao;

import org.mirasruntime.springjdbcproject.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    Product findById(int id);

    Product create(Product product);

    Product update(Product product);

    void deleteById(int id);
}
