package balint.kovacs.first_project.database;

import balint.kovacs.first_project.model.Role;
import balint.kovacs.first_project.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(String username, String name, String password){
        jdbcTemplate.update("INSERT INTO users(username, name, password) VALUES (?,?,?)", username, name, password);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String pass = resultSet.getString("password");
            Role role = Role.valueOf(resultSet.getString("role"));
            return new User(id, username, name, pass, role);
        }
    }

    public List<User> listUsers(){
        return jdbcTemplate.query("SELECT id, username, name, password, role FROM users", new UserRowMapper());
    }

    public void updateUser(long id, String name, String password){
        if(password.isEmpty()) {
            jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?", name, id);
        } else {
            jdbcTemplate.update("UPDATE users SET name = ?, password = ? WHERE id = ?", name, password, id);
        }
    }

    public void deleteUser(long id){
        jdbcTemplate.update("DELETE FROM income WHERE user_id = ?", id);
        jdbcTemplate.update("DELETE FROM spend WHERE user_id = ?", id);
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    public User findUserByUsername(String username){
        return jdbcTemplate.queryForObject
                ("SELECT id, username, name, password, role FROM users WHERE username = ?", new UserRowMapper(), username);
    }

}
