package balint.kovacs.first_project.database;

import balint.kovacs.first_project.model.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class IncomeDao {
    private JdbcTemplate jdbcTemplate;

    public IncomeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addIncome(long value, long userId){
        jdbcTemplate.update("INSERT INTO income(user_id, income_date, income_value)" +
                "VALUES(?, NOW() ,?)", userId, value);
        jdbcTemplate.update("UPDATE users SET wallet = wallet + ? WHERE id = ?", value, userId);
    }

    public void modifyIncome(long value, long incomeId){
        jdbcTemplate.update("UPDATE income SET income_value = ? WHERE id = ?", value, incomeId);
    }

    public List<Income> listIncomes(long userId){
        return jdbcTemplate.query("SELECT income_date, income_value, id FROM income WHERE user_id = ? " +
                        "AND YEAR(income_date) = YEAR(NOW()) " +
                        "ORDER BY income_date DESC",
                new IncomeRowMapper(), userId);
    }

    public List<Income> actualMonthIncomes(long userId){
        return jdbcTemplate.query("SELECT income_date, income_value, id FROM income WHERE user_id = ? " +
                "AND YEAR(income_date) = YEAR(NOW()) " +
                        "AND MONTH(income_date) = MONTH(NOW()) " +
                        "ORDER BY income_date DESC",
                new IncomeRowMapper(), userId);
    }

    private static class IncomeRowMapper implements RowMapper<Income> {
        @Override
        public Income mapRow(ResultSet resultSet, int i) throws SQLException {
            long value = resultSet.getLong("income_value");
            LocalDate time = resultSet.getDate("income_date").toLocalDate();
            long id = resultSet.getLong("id");
            return new Income(value, time,id);
        }
    }

    public void deleteIncome(long incomeId){
        jdbcTemplate.update("DELETE FROM income WHERE id = ?", incomeId);
    }
}
