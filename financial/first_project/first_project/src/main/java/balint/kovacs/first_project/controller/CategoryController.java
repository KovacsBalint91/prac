package balint.kovacs.first_project.controller;

import balint.kovacs.first_project.model.Category;
import balint.kovacs.first_project.model.Response;
import balint.kovacs.first_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/api/category/add/{categoryName}", method = RequestMethod.POST)
    public Response createCategory(@PathVariable String categoryName, Authentication authentication){
        return categoryService.createCategory(categoryName);
    }

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    public Optional<List<Category>> listCategories(){
        return categoryService.listCategories();
    }

    @RequestMapping(value = "/api/category/{categoryId}", method = RequestMethod.POST)
    public Category findCategoryById(@PathVariable long categoryId, Authentication authentication){
        return categoryService.findCategoryById(categoryId);
    }

}
