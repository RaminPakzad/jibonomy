package sadad.com.jibonomy.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sadad.com.jibonomy.entities.Wish;


@Dao
public interface WishDao {
    @Insert
    void insert(Wish word);

    @Query("DELETE FROM Wish")
    void deleteAll();

    @Query("SELECT * from Wish ORDER BY wishId ASC")
    List<Wish> getAllWishes();

    @Query("SELECT * from Wish ORDER BY wishId ASC")
    List<Wish> getAll();

    @Query("SELECT * from Wish where  wishId = :wishId  ")
    Wish get(Long wishId);
    @Query("delete from Wish where  wishId = :wishId ")
    void delete(Long wishId);
}
