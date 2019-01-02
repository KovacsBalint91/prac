package balint.kovacs.first_project.service;

import balint.kovacs.first_project.database.UserDao;
import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;


@Service
public class UserService {

    private UserDao userDao;
    private ResourceBundle bundle;
    private final String REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.bundle = ResourceBundle.getBundle("userMessages");
    }

    public Response createUser(String username, String name, String password, String confirmPassword){
        List<User> users = userDao.listUsers();
        if(users.stream().noneMatch(user -> user.getUsername().equals(username)) && password.equals(confirmPassword)
        && confirmPassword.matches(REGEX)){
            userDao.createUser(username,name, new BCryptPasswordEncoder().encode(password));
        } else if (users.stream().anyMatch(user -> user.getUsername().equals(username))){
            return new Response(false, bundle.getString("label.usedUsername"));
        } else if (!password.equals(confirmPassword)){
            return new Response(false, bundle.getString("label.passwordsDontMatch"));
        } else if (!password.matches(REGEX)){
            return new Response(false, bundle.getString("label.passwordIsWeak"));
        }
        return new Response(true, name + "( " + username + " )" + bundle.getString("label.createUser"));
    }

    public Response updateUser(String username, String name, String password, String confirmPass){
        User user = userDao.findUserByUsername(username);
        long id = user.getId();
        if(password.matches(REGEX) && password.equals(confirmPass)) {
            userDao.updateUser(id, name, new BCryptPasswordEncoder().encode(password));
        } else if(password.isEmpty() && confirmPass.isEmpty()){
            userDao.updateUser(id, name, "");
        } else if(!password.equals(confirmPass)){
            return new Response(false, bundle.getString("label.passwordsDontMatch"));
        } else if(!password.matches(REGEX)){
            return new Response(false, bundle.getString("label.passwordIsWeak"));
        }
        return new Response(true, name + "( " + username + " )" + bundle.getString("label.updateUser"));
    }

    public Response deleteUser(String username){
        long id = userDao.findUserByUsername(username).getId();
        userDao.deleteUser(id);
        return new Response(true, bundle.getString("label.deleteUser"));
    }

    public List<User> listUsers(){
        return userDao.listUsers();
    }

    public User findUserByName(String name){
        return userDao.findUserByUsername(name);
    }
}
