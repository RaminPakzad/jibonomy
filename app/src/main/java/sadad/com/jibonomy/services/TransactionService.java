package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.List;

import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.TransactionDao;
import sadad.com.jibonomy.entities.Transaction;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class TransactionService {
    private Context context;
    private TransactionDao transactionDao;

    public TransactionService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.transactionDao = db.transactionDao();
        this.context = context;
    }

    public void insert(Transaction transaction) {
        this.transactionDao.insert(transaction);
    }
    public void delete(Long transactionId) {
        this.transactionDao.delete(transactionId);
    }

    public List<Transaction> getTransactiones() {
        return transactionDao.getAll();
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionDao.get(transactionId);
    }

    public void deleteAll() {
        transactionDao.deleteAll();
    }

}