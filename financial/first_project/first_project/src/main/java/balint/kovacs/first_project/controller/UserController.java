package balint.kovacs.first_project.controller;

import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.model.User;
import balint.kovacs.first_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public Optional<User> actualUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        return Optional.of(user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public Response createUser(@RequestBody User user) {
        return userService.createUser(user.getUsername(), user.getName(), user.getPassword(), user.getConfirmPassword());
    }

    @RequestMapping(value = "/api/listusers", method = RequestMethod.GET)
    public List<User> listUsers(Authentication authentication) {
                return userService.listUsers();
    }

    @RequestMapping(value = "/api/users/delete", method = RequestMethod.DELETE)
    public Response deleteUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.deleteUser(username);
    }

    @RequestMapping(value = "/api/users/{username}", method = RequestMethod.POST)
    public void updateUser(@RequestBody User user, @PathVariable String username) {
        userService.updateUser(username, user.getName(), user.getPassword(), user.getConfirmPassword());
    }

}
