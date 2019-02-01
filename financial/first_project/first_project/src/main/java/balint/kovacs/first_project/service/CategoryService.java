package balint.kovacs.first_project.service;

import balint.kovacs.first_project.database.CategoryDao;
import balint.kovacs.first_project.model.Category;
import balint.kovacs.first_project.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class CategoryService {

    private CategoryDao categoryDao;
    private ResourceBundle bundle;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        this.bundle = ResourceBundle.getBundle("categoryMessages");
    }

    public Response createCategory(String name){
        List<String> categories = new ArrayList<>();
        categoryDao.listCategories().stream().forEach(c -> categories.add(c.getName().toLowerCase()));
        if(categories.contains(name.toLowerCase().trim())){
            return new Response(false, bundle.getString("label.existCategory"));
        } else {
            categoryDao.createCategory(name);
            return new Response(true, bundle.getString("label.createCategory"));
        }
    }

    public Optional<List<Category>> listCategories(){
        return Optional.of(categoryDao.listCategories());
    }

    public Category findCategoryById(long categoryId){
        return categoryDao.findCategoryById(categoryId);
    }
}
