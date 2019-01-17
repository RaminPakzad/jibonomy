package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.List;

import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.SubCategoryDao;
import sadad.com.jibonomy.entities.SubCategory;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class SubCategoryService {
    private Context context;
    private SubCategoryDao subCategoryDao;

    public SubCategoryService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.subCategoryDao = db.subCategoryDao();
        this.context = context;
    }

    public void insert(SubCategory subCategory) {
        this.subCategoryDao.insert(subCategory);
    }

    public void delete(Long subCategoryId) {
        this.subCategoryDao.delete(subCategoryId);
    }

    public List<SubCategory> getSubCategoryes() {
        return subCategoryDao.getAll();
    }

    public SubCategory getSubCategory(Long subCategoryId) {
        return subCategoryDao.get(subCategoryId);
    }

    public void deleteAll() {
        subCategoryDao.deleteAll();
    }

    public List<SubCategory> getAllByCategoryId(Long categoryId) {
        return subCategoryDao.getAllByCategoryId(categoryId);
    }

    public void deleteByCategoryIdAndSubCategoryName(Long categoryId, String subCategoryName) {
        subCategoryDao.deleteByCategoryIdAndSubCategoryName(categoryId, subCategoryName);
    }
}
