package sadad.com.jibonomy.dao;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.math.BigDecimal;
import java.util.List;

import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.entities.Wish;
import sadad.com.jibonomy.utils.StringUtil;

import static sadad.com.jibonomy.utils.StringUtil.UNDEFINED_TAG;

@Database(entities = {Wish.class, Transaction.class, Category.class, SubCategory.class}, version = 9, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class JibonomyRoomDatabase extends RoomDatabase {
    private static volatile JibonomyRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static JibonomyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JibonomyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JibonomyRoomDatabase.class, "jibonomy_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WishDao wishDao();

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    public abstract SubCategoryDao subCategoryDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WishDao asyncWishDao;
        private final TransactionDao asyncTransactionDao;
        private final CategoryDao asyncCategoryDao;
        private final SubCategoryDao asyncSubCategoryDao;

        PopulateDbAsync(JibonomyRoomDatabase db) {
            asyncWishDao = db.wishDao();
            asyncTransactionDao = db.transactionDao();
            asyncCategoryDao = db.categoryDao();
            asyncSubCategoryDao = db.subCategoryDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

}