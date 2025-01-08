package org.mirasruntime.springjdbcproject.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.springjdbcproject.dao.ProductDao;
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
    private static final String SELECT = """
            select p.id    as product_id,
                   p.name  as product_name,
                   p.price as product_price,
                   c.id    as category_id,
                   c.name  as category_name
            from products p
            join categories c on p.category_id = c.id
            """;


    @Override
    public List<Product> findAll() {
        System.out.println(SELECT);
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Product findById(int id) {
        String sql = SELECT + "where p.id = ?";
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
        int id = rs.getInt("product_id");
        String name = rs.getString("product_name");
        Double price = rs.getDouble("product_price");

        Integer categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("category_name");

        Category category = new Category(categoryId, categoryName);

        return new Product(id, name, price, category);
    }
}
