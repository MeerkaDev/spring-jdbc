package org.mirasruntime.springjdbcproject.dao;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.springjdbcproject.model.Category;
import org.mirasruntime.springjdbcproject.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Product> findAll() {
        String sql = "select * from products";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Product findById(int id) {
        String sql = "select * from products where id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Product create(Product product) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("name", product.getName());
        map.put("price", product.getPrice());
        map.put("category_id", product.getCategory().getId());

        Number number = insert.executeAndReturnKey(map);

        int id = number.intValue();
        product.setId(id);

        return product;
    }

    @Override
    public Product update(Product product) {
        String sql = "update products set name = ?, price = ?, category_id = ? where id = ?";
        int recChanged = jdbcTemplate.update(sql, product.getName(),
                product.getPrice(), product.getCategory().getId(), product.getId());

        if (recChanged > 0) {
            return product;
        }

        return null;
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from products where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Double price = rs.getDouble("price");
        Integer category_id = rs.getInt("category_id");
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(jdbcTemplate);
        Category category = categoryDao.findById(category_id);
        return new Product(id, name, price, category);
    }
}
