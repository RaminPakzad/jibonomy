package sadad.com.jibonomy.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sadad.com.jibonomy.entities.SubCategory;


@Dao
public interface SubCategoryDao {
    @Insert
    void insert(SubCategory word);

    @Query("DELETE FROM SubCategory")
    void deleteAll();

    @Query("SELECT * from SubCategory ORDER BY subCategoryId ASC")
    List<SubCategory> getAllSubCategories();

    @Query("SELECT * from SubCategory where categoryId = :categoryId ORDER BY subCategoryId ASC")
    List<SubCategory> getAllByCategoryId(Long categoryId);

    @Query("delete from SubCategory where categoryId = :categoryId and subCategoryName = :subCategoryName")
    void deleteByCategoryIdAndSubCategoryName(Long categoryId, String subCategoryName);

    @Query("SELECT * from SubCategory ORDER BY subCategoryId ASC")
    List<SubCategory> getAll();

    @Query("SELECT * from SubCategory where  subCategoryId = :subCategoryId  ")
    SubCategory get(Long subCategoryId);

    @Query("delete from SubCategory where  subCategoryId = :subCategoryId ")
    void delete(Long subCategoryId);
}
