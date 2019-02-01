package balint.kovacs.first_project.database;

import balint.kovacs.first_project.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDao {
    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Category category = new Category(id, name);
            return category;
        }
    }

    public Category findCategoryById(long categoryId){
        return jdbcTemplate.queryForObject("SELECT id, name FROM categories WHERE id = ?",
                new CategoryRowMapper(), categoryId);
    }

    public void createCategory(String name){
        jdbcTemplate.update("INSERT INTO categories (name)" +
                "VALUES(?)", name);
    }

    public List<Category> listCategories(){
        return jdbcTemplate.query("SELECT id, name FROM categories ORDER BY name", new CategoryRowMapper());
    }

    public void deleteCategory(long id){
        jdbcTemplate.update("DELETE FROM categories WHERE id = ?", id);
    }



}
