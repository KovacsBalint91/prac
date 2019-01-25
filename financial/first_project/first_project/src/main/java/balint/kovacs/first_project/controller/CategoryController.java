package balint.kovacs.first_project.controller;

import balint.kovacs.first_project.model.Category;
import balint.kovacs.first_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    public Optional<List<Category>> listCategories(){
        return categoryService.listCategories();
    }

}
