package balint.kovacs.first_project.service;

import balint.kovacs.first_project.database.IncomeDao;
import balint.kovacs.first_project.database.UserDao;
import balint.kovacs.first_project.model.Income;
import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;

@Service
public class IncomeService {

    private IncomeDao incomeDao;
    private UserDao userDao;
    private ResourceBundle bundle;

    public IncomeService(IncomeDao incomeDao, UserDao userDao) {
        this.incomeDao = incomeDao;
        this.userDao = userDao;
        bundle = ResourceBundle.getBundle("incomeMessages");
    }

    public Response addIncome(String username, long value){
        User user = userDao.findUserByUsername(username);
        if(value <= 0){
            return new Response(false, bundle.getString("label.falseIncomeValue"));
        } else {
            incomeDao.addIncome(value, user.getId());
            return new Response(true, bundle.getString("label.addIncomeValue"));
        }
    }

    public Response modifyIncome(long value, long incomeId){
        if(value == 0){
            incomeDao.deleteIncome(incomeId);
            return new Response(true, bundle.getString("label.deleteIncome"));
        }
        if(value < 0){
            return new Response(false, bundle.getString("label.falseIncomeValue"));
        } else {
            incomeDao.modifyIncome(value, incomeId);
            return new Response(true, bundle.getString("label.modifyIncome"));
        }
    }

    public List<Income> listIncomes(long userId){
        return incomeDao.listIncomes(userId);
    }

    public List<Income> actualMonthIncomes(long userId){
        return incomeDao.actualMonthIncomes(userId);
    }
}
