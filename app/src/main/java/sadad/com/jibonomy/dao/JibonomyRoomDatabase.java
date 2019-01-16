package sadad.com.jibonomy.dao;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.entities.Wish;

@Database(entities = {Wish.class, Transaction.class, Category.class, SubCategory.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class JibonomyRoomDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile JibonomyRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
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
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
//            mDao.deleteAll();
//
//            Wish word = new Wish("", "", "", BigDecimal.ONE);
//            mDao.insert(word);
//            word = new Wish("", "", "", BigDecimal.TEN);
//            mDao.insert(word);

            asyncCategoryDao.deleteAll();
            asyncSubCategoryDao.deleteAll();

            Category category = new Category();
            category.setCategoryId(1L);
            category.setCategoryName("cat1");
            category.setIconName("home");

            Category category1 = new Category();
            category.setCategoryId(2L);
            category1.setCategoryName("cat2");
            category1.setIconName("taxi");

            asyncCategoryDao.insert(category);
            asyncCategoryDao.insert(category1);

            SubCategory subCategory1=new SubCategory();
            subCategory1.setCategoryId(1L);
            subCategory1.setSubCategoryName("sub2");
            subCategory1.setIconName("home");

            SubCategory subCategory2=new SubCategory();
            subCategory2.setCategoryId(1L);
            subCategory2.setSubCategoryName("sub3");
            subCategory2.setIconName("home");

            SubCategory subCategory3=new SubCategory();
            subCategory3.setCategoryId(2L);
            subCategory3.setSubCategoryName("sub4");
            subCategory3.setIconName("home");

            SubCategory subCategory4=new SubCategory();
            subCategory4.setCategoryId(2L);
            subCategory4.setSubCategoryName("sub5");
            subCategory4.setIconName("home");

            asyncSubCategoryDao.insert(subCategory1);
            asyncSubCategoryDao.insert(subCategory2);
            asyncSubCategoryDao.insert(subCategory3);
            asyncSubCategoryDao.insert(subCategory4);

            return null;
        }
    }

}