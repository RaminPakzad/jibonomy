package sadad.com.jibonomy.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sadad.com.jibonomy.entities.Transaction;


@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Query("DELETE FROM Transactions")
    void deleteAll();

    @Query("SELECT * from Transactions ORDER BY transactionId ASC")
    List<Transaction> getAllTransactiones();

    @Query("SELECT * from Transactions ORDER BY transactionId ASC")
    List<Transaction> getAll();

    @Query("SELECT * from Transactions where  transactionId = :transactionId ")
    Transaction get(Long transactionId);

    @Query("delete from Transactions where  transactionId = :transactionId")
    void delete(Long transactionId);
}
