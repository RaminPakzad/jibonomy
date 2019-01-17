package sadad.com.jibonomy.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sadad.com.jibonomy.entities.Category;


@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Query("DELETE FROM Category")
    void deleteAll();

    @Query("SELECT * from Category ORDER BY categoryId ASC")
    List<Category> getAllCategories();

    @Query("SELECT * from Category ORDER BY categoryId ASC")
    List<Category> getAll();

    @Query("SELECT * from Category where  categoryId = :categoryId  ")
    Category get(Long categoryId);

    @Query("delete from Category where  categoryId = :categoryId ")
    void delete(Long categoryId);
}
