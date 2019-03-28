package balint.kovacs.first_project.controller;

import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.model.Spend;
import balint.kovacs.first_project.model.User;
import balint.kovacs.first_project.service.SpendService;
import balint.kovacs.first_project.service.UserService;
import balint.kovacs.first_project.view.ExcelSpendView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.ResourceBundle;

@RestController
public class SpendController {

    @Autowired
    private SpendService spendService;
    @Autowired
    private UserService userService;

    private ResourceBundle bundle = ResourceBundle.getBundle("spendMessages");

    @RequestMapping(value = "/api/spend/{username}", method = RequestMethod.POST)
    public Response addSpend(@RequestBody Spend spend, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        spendService.addSpend(spend.getValue(), user.getId(), spend.getCategory_id());
        return new Response(true, bundle.getString("label.addSpend"));
    }

    @RequestMapping(value = "/api/modifyspend/{id}", method = RequestMethod.POST)
    public Response updateSpend(@RequestBody Spend spend, @PathVariable long id) {
        spendService.updateSpend(id, spend.getValue(), spend.getCategory_id());
        return new Response(true, bundle.getString("label.updateSpend"));
    }

    @RequestMapping(value = "/api/spendlist", method = RequestMethod.GET)
    public List<Spend> listSpends(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        return spendService.listSpends(user.getId());
    }

    @RequestMapping(value = "/api/deletespend/{id}", method = RequestMethod.DELETE)
    public Response deleteSpend(@PathVariable long id) {
        spendService.deleteSpend(id);
        return new Response(true, bundle.getString("label.deleteSpend"));
    }

    @RequestMapping(value = "/api/filteredspends/{username}", method = RequestMethod.GET)
    public List<Spend> actualMonthUserSpends(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        return spendService.actualMonthUserSpends(user.getId());
    }

    @RequestMapping(value = "/spendsreport", method = RequestMethod.GET)
    public ModelAndView getSpends(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        List<Spend> spendList = spendService.listSpends(user.getId());

        return new ModelAndView(new ExcelSpendView(), "spendList", spendList);
    }


}
