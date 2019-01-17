package sadad.com.jibonomy.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sadad.com.jibonomy.biz.dto.ChartDataDto;
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

    @Query("select SubCategory.categoryId as lable ,SUM(Transactions.amount) as totalAmount  from Transactions left join SubCategory on Transactions.subCategoryType =  SubCategory.categoryId group by SubCategory.categoryId")
    List<ChartDataDto> sumAmountByCategoryGroup();

    @Query("select SubCategory.categoryId as lable ,SUM(Transactions.amount) as totalAmount  from Transactions left join SubCategory on Transactions.subCategoryType =  SubCategory.categoryId WHERE substr(Transactions.transactionDate,5,2) == :month  group by SubCategory.categoryId ")
    List<ChartDataDto> sumAmountByCategoryGroupWithMonth(String month);





}
