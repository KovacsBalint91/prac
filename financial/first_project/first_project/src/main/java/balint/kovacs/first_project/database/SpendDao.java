package balint.kovacs.first_project.database;

import balint.kovacs.first_project.model.Spend;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SpendDao {

    private JdbcTemplate jdbcTemplate;

    public SpendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class SpendRowMapper implements RowMapper<Spend>{
        @Override
        public Spend mapRow(ResultSet resultSet, int i) throws SQLException {
            long value = resultSet.getLong("spend_value");
            LocalDate time = resultSet.getDate("spend_date").toLocalDate();
            long categoryId = resultSet.getLong("category_id");
            return new Spend(value, time, categoryId);
        }
    }

    public void addSpend(long value, long userId, long categoryId){
        jdbcTemplate.update("INSERT INTO spend(user_id, spend_date, spend_value, category_id) " +
                "VALUES(?, NOW(), ?, ?)", userId, value, categoryId);
    }

    public void modifySpend(long spendId, long value, long category_id){
        jdbcTemplate.update("UPDATE spend SET spend_value = ?, category_id = ? WHERE id = ?", value, category_id, spendId);
    }

    public List<Spend> listUserSpend(long userId){
        return jdbcTemplate.query("SELECT spend_date, spend_value, category_id FROM spend WHERE user_id = ?", new SpendRowMapper(), userId);
    }

    public void deleteSpend(long spendId){
        jdbcTemplate.update("DELETE FROM spend WHERE id = ?", spendId);
    }

}
