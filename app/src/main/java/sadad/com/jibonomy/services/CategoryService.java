package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.List;

import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.CategoryDao;
import sadad.com.jibonomy.entities.Category;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class CategoryService {
    private Context context;
    private CategoryDao categoryDao;

    public CategoryService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.categoryDao = db.categoryDao();
        this.context = context;
    }

    public void insert(Category category) {
        this.categoryDao.insert(category);
    }
    public void delete(Long categoryId) {
        this.categoryDao.delete(categoryId);
    }

    public List<Category> getCategories() {
        return categoryDao.getAll();
    }

    public Category getCategory(Long categoryId) {
        return categoryDao.get(categoryId);
    }

    public void deleteAll() {
        categoryDao.deleteAll();
    }


    public Category get( long id ) {
        return categoryDao.get(id);
    }

}
