package balint.kovacs.first_project.service;

import balint.kovacs.first_project.database.SpendDao;
import balint.kovacs.first_project.database.UserDao;
import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.model.Spend;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;

@Service
public class SpendService {

    private SpendDao spendDao;
    private UserDao userDao;
    private ResourceBundle bundle;

    public SpendService(SpendDao spendDao, UserDao userDao) {
        this.spendDao = spendDao;
        this.userDao = userDao;
        this.bundle = ResourceBundle.getBundle("spendMessages");
    }

    public Response addSpend(long value, long userId, long categoryId) {
        long tempId = categoryId;
        if(categoryId == 0L){
            tempId = 1;
        }
        if (value > 0) {
            spendDao.addSpend(value, userId, tempId);
            return new Response(true, bundle.getString("label.addSpend"));
        } else {
            return new Response(false, bundle.getString("label.wrongAddSpend"));
        }
    }

    public Response updateSpend(long spendId, long value, long categoryId){
        spendDao.modifySpend(spendId, value, categoryId);
        return new Response(true, bundle.getString("label.updateSpend"));
    }

    public List<Spend> listSpends(long userId){
        return spendDao.listUserSpend(userId);
    }

    public Response deleteSpend(long spendId){
        spendDao.deleteSpend(spendId);
        return new Response(true, bundle.getString("label.deleteSpend"));
    }
}
