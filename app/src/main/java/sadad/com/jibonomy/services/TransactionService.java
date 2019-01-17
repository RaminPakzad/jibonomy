package sadad.com.jibonomy.services;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sadad.com.jibonomy.biz.dto.ChartDataDto;
import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.TransactionDao;
import sadad.com.jibonomy.entities.Category;
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

    public List<PieEntry> getChartData(){
        List<PieEntry> chart = new ArrayList<>();
        List<ChartDataDto> res = transactionDao.sumAmountByCategoryGroup();
        for (ChartDataDto item:res){
            Log.d("getChartData", "dd" );
            if( item.getLable() != null){
                chart.add( new PieEntry(item.getTotalAmount(),item.getLable() ));
            }
        }
        return chart;
    }

}
