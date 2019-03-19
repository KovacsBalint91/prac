package balint.kovacs.first_project;


import balint.kovacs.first_project.controller.IncomeController;
import balint.kovacs.first_project.controller.UserController;
import balint.kovacs.first_project.model.Income;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clear.sql")
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class IncomeControllerTest {

    @Autowired
    IncomeController incomeController;
    Authentication authentication;
    Income income;
    List<Income> incomes = new ArrayList<>();

    @Before
    public void init(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        income = new Income(10000);
    }

    @Test
    public void testAddIncome(){
        assertEquals("The amount added!",
                incomeController.addIncome(income, authentication).getMessage()
                );
        incomes = incomeController.listIncomes(authentication);
        assertEquals(1, incomes.size());
    }

    @Test
    public void testModifyIncome(){
        incomeController.addIncome(income, authentication);
        long id = incomeController.listIncomes(authentication).get(0).getId();
        assertEquals("You modified the amount successfully!",
                incomeController.modifyIncome(authentication,new Income(20000), id).getMessage());
        assertEquals(1, incomeController.listIncomes(authentication).size());
        assertEquals(20000, incomeController.listIncomes(authentication).get(0).getValue());
    }

    @Test
    public void testActualMonthIncomes(){
        incomeController.addIncome(income, authentication);
        List<Income> actualIncomes = incomeController.actualMonthIncomes(authentication);
        assertEquals(1, actualIncomes.size());
    }

}
