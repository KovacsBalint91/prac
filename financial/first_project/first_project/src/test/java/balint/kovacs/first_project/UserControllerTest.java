package balint.kovacs.first_project;


import balint.kovacs.first_project.controller.UserController;
import balint.kovacs.first_project.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clear.sql")
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class UserControllerTest {

    @Autowired
    private UserController userController;
    private User user;
    private Authentication authentication;

    @Before
    public void init(){
        user = new User.Builder()
                .name("Horváth Róbert")
                .username("horvathrobi")
                .password("Roberto123")
                .confirmPassword("Roberto123")
                .build();
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    @Test
    public void testCreateUser(){
        userController.createUser(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(3, userController.listUsers(authentication).size());

    }

    @Test
    public void testDeleteUser(){
        userController.createUser(user);
        userController.deleteUser("horvathrobi");
        assertTrue(
                userController.listUsers(authentication).stream()
                        .noneMatch(u -> u.getUsername().equals("horvathrobi")));
    }

    @Test
    public void testUpdateUser(){
        userController.createUser(user);
        User updated = new User.Builder()
                .name("Kovács Róbert")
                .password("Robi112233")
                .confirmPassword("Robi112233")
                .build();
        userController.updateUser(updated, user.getUsername());
        assertTrue(userController.listUsers(authentication).stream()
        .noneMatch(u -> u.getName().equals("Horváth Róbert")));

        assertTrue(userController.listUsers(authentication).stream()
        .anyMatch(u -> u.getName().equals("Kovács Róbert")));
    }

    @Test
    public void testCreateUserWithExistingUsername(){
        userController.createUser(user);
        assertEquals("The nickname is already used!",
                userController.createUser(user).getMessage());
    }

    @Test
    public void testCreateUserWithDifferentPasswords(){
        User testUser = new User.Builder()
                .name("Szilágyi Zakariás")
                .username("szilagyizakarias")
                .password("Zakarias12")
                .confirmPassword("Zakarias45")
                .build();
        assertEquals("The passwords do not match!",
                userController.createUser(testUser).getMessage());
    }

    @Test
    public void testCreateUserWithWeakPassword(){
        User testUser = new User.Builder()
                .name("Szilágyi Zakariás")
                .username("szilagyizakarias")
                .password("zakarias12")
                .confirmPassword("zakarias12")
                .build();
        assertEquals("The password is weak!",
                userController.createUser(testUser).getMessage());
    }
}
