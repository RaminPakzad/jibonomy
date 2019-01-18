package sadad.com.jibonomy.services;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sadad.com.jibonomy.biz.dto.ChartDataDto;
import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.TransactionDao;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.utils.NotifyUtil;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class TransactionService {
    private Context context;
    private TransactionDao transactionDao;
    private UserService userService;

    private SubCategoryService subCategoryService;

    public TransactionService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.transactionDao = db.transactionDao();
        this.userService = new UserService(context);
        this.subCategoryService = new SubCategoryService(context);
        this.context = context;
    }


    public List<Transaction> getTransactionsByAmountAndDateTime(BigDecimal amount, String date, String time) {
        return transactionDao.getByAmountAndDateTime(amount, date, time);
    }

    public void insert(Transaction transaction) {
        if (/*isSMS && */isExist(transaction)) {
            Log.i("jibonomy", "duplicated transaction");
            return;
        }
        boolean isSMS = transaction.getTag() != null && transaction.getTag().equals("SMS");
        if (isSMS) {
            NotifyUtil.sendNotification(context,"پیام بانکی جدید","برای تعیین تراکنش لمس کنید");
        }

        expenseExceedNotify(transaction);
        this.transactionDao.insert(transaction);
    }

    private boolean isExist(Transaction transaction) {
        try {
            List<Transaction> transactions = getTransactionsByAmountAndDateTime(transaction.getAmount(), transaction.getTransactionDate(), transaction.getTransactionTime());
            return transactions.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void update(Transaction transaction) {
        expenseExceedNotify(transaction);

        this.transactionDao.delete(transaction.getTransactionId());
        this.transactionDao.insert(transaction);
    }

    private void expenseExceedNotify(Transaction transaction) {
        if (transaction.getTransactionType().equals(Transaction.EXPENSE)) {
            PersianCalendar persianCalendar = new PersianCalendar(new Date().getTime());
            String persianDate = persianCalendar.getPersianShortDate().replace("/", "");
            BigDecimal sumOfExpenseTransactions = getSumOfExpenseTransactions(persianDate);
            BigDecimal sum = sumOfExpenseTransactions.add(transaction.getAmount());
            if (sum.compareTo(userService.getUserDailyBudget()) > 0) {
                NotifyUtil.sendNotification(context,"عبور از سقف بودجه تعیین شده","شما امروز بیشتر از سقف بودجه خرج کرده اید");
            }
        }
    }

    public void delete(Long transactionId) {
        this.transactionDao.delete(transactionId);
    }

    public List<Transaction> getTransactiones() {
        return transactionDao.getAll();
    }

    public BigDecimal getSumOfExpenseTransactions(String transactionDate) {
        BigDecimal sumTransactions = BigDecimal.ZERO;
        List<Transaction> transactions = transactionDao.getByTransactionDate(transactionDate);
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType().equals(Transaction.EXPENSE)) {
                sumTransactions = sumTransactions.add(transaction.getAmount());
            }
        }
        return sumTransactions;
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionDao.get(transactionId);
    }

    public void deleteAll() {
        transactionDao.deleteAll();
    }


    public List<PieEntry> getChartData(int month, boolean mock) {
        List<PieEntry> chart = new ArrayList<>();
        if (mock == true) {
            String m = (month < 10 ? "0" + month : "" + month);
            CategoryService categoryService = new CategoryService(context);
            List<Category> cl = categoryService.getCategories();
            for (Category c : cl) {
                chart.add(new PieEntry((long) Math.floor(Math.random() * 1000) * 1000, c.getCategoryName()));
            }
        } else {
            String m = (month < 10 ? "0" + month : "" + month);
            List<ChartDataDto> res = transactionDao.sumAmountByCategoryGroupWithMonth(m);
            for (ChartDataDto item : res) {
                if (item.getLable() != null) {
                    chart.add(new PieEntry(item.getTotalAmount(), item.getLable()));
                }
            }
        }
        return chart;
    }public List<BarEntry> getBarChartData(int month, boolean mock) {
        List<BarEntry> chart = new ArrayList<>();
        if (mock == true) {
        } else {
            String m = (month < 10 ? "0" + month : "" + month);
            List<ChartDataDto> res = transactionDao.sumAmountByCategoryGroupWithMonth(m);
            for (ChartDataDto item : res) {
                if (item.getLable() != null) {
                    chart.add(new BarEntry(item.getTotalAmount(), item.getTotalAmount()));
                }
            }
        }
        return chart;
    }

}
