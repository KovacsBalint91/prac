package balint.kovacs.first_project.controller;

import balint.kovacs.first_project.model.Income;
import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.service.IncomeService;
import balint.kovacs.first_project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
public class IncomeController {

    private IncomeService incomeService;
    private UserService userService;
    private ResourceBundle bundle;

    public IncomeController(IncomeService incomeService, UserService userService) {
        this.userService = userService;
        this.incomeService = incomeService;
        bundle = ResourceBundle.getBundle("incomeMessages");
    }

    @RequestMapping(value = "/api/addincome/{username}", method = RequestMethod.POST)
    public Response addIncome(@RequestBody Income income, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return incomeService.addIncome(userDetails.getUsername(), income.getValue());
    }

    @RequestMapping(value = "/api/modifyincome/{username}/{id}", method = RequestMethod.POST)
    public Response modifyIncome( Authentication authentication, @RequestBody Income income, @PathVariable long id) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return incomeService.modifyIncome(income.getValue(), id);
        }
        return new Response(false, bundle.getString("label.wrongAuth"));
    }

    @RequestMapping(value = "/api/incomes/{username}", method = RequestMethod.GET)
    public List<Income> listIncomes(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long id = userService.findUserByName(userDetails.getUsername()).getId();
        return incomeService.listIncomes(id);
    }
}
