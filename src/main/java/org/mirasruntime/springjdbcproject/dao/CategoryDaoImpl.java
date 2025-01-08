package org.mirasruntime.springjdbcproject.dao;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.springjdbcproject.model.Category;
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
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    // query - select
    // update - insert/update/delete

    @Override
    public List<Category> findAll() { // select from categories
        String sql = "select * from categories";
/*        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        List<Category> categories = new ArrayList<>();
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt(1);
            String name = sqlRowSet.getString(2);
            Category category = new Category(id, name);
            categories.add(category);
        }*/
        /*RowMapper<Category> rowMapper = new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                return category;
            }
        };*/
//        RowMapper<Category> rowMapper = (ResultSet rs, int num) -> mapRow(rs, num);
//        RowMapper<Category> rowMapper = this::mapRow;


//        return categories;
//        return jdbcTemplate.query(sql, rowMapper);
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class)); // только если названия совпадают
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Category findById(int id) { // select from categories where id = ?
        String sql = "select * from categories where id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Category create(Category category) {// insert into categories (name) values (?)
        // insert into categories (name) valuse ("Test Category")
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id");

        Map <String, Object> map = new HashMap<>();
        map.put("name", category.getName());

        Number number = insert.executeAndReturnKey(map);

        int id = number.intValue();
        category.setId(id);
//        String sql = "insert into categories (name) values (?)";
//        jdbcTemplate.update(sql, category.getName());
        return category;
    }

    @Override
    public Category update(Category category) {
        String sql = "update categories set name = ? where id = ?";
        int recChanged = jdbcTemplate.update(sql, category.getName(), category.getId());

        if (recChanged > 0) {
            return category;
        }

        return null;
    }

    @Override
    public void deleteById(int id) { // delete from categories where id = ?
        String sql = "delete from categories where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Category mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Category(id, name);
    }
}
