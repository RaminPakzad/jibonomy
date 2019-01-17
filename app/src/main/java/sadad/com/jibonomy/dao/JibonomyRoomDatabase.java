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
            asyncTransactionDao.deleteAll();

            Category category = new Category();
            category.setCategoryId(1L);
            category.setCategoryName("خوراک");
            category.setBudget(new BigDecimal(22222));
            category.setIconName("ic_food_grey600_24dp");

            Category category1 = new Category();
            category1.setCategoryId(2L);
            category1.setBudget(new BigDecimal(22222));

            category1.setCategoryName("پوشاک");
            category1.setIconName("ic_tshirt_crew_grey600_24dp");


            Category category2 = new Category();
            category2.setCategoryId(3L);
            category2.setBudget(new BigDecimal(22222));

            category2.setCategoryName("حمل و نقل");
            category2.setIconName("ic_train_car_grey600_24dp");


            Category category3 = new Category();
            category3.setCategoryId(4L);
            category3.setCategoryName("فرهنگی");
            category3.setBudget(new BigDecimal(22222));

            category3.setIconName("ic_theater_grey600_24dp");


            Category category4 = new Category();

            category4.setBudget(new BigDecimal(22222));

            category4.setCategoryName("درمانی");
            category4.setIconName("ic_hospital_building_grey600_24dp");

////////////////////////undefined
            Category undefinedCategory = new Category();
            undefinedCategory.setBudget(new BigDecimal(1000000000));
            undefinedCategory.setCategoryName("نامشخص");
            undefinedCategory.setTag(UNDEFINED_TAG);
            undefinedCategory.setIconName("question");
////////////////////////undefined
            asyncCategoryDao.insert(category);
            asyncCategoryDao.insert(category1);
            asyncCategoryDao.insert(category2);
            asyncCategoryDao.insert(category3);
            asyncCategoryDao.insert(undefinedCategory);
//////////////////////////
            SubCategory subCategory1 = new SubCategory();
            subCategory1.setSubCategoryName("sub2");
            subCategory1.setIconName("home");

            SubCategory subCategory2 = new SubCategory();
            subCategory2.setSubCategoryName("sub3");
            subCategory2.setIconName("home");

            SubCategory subCategory3 = new SubCategory();
            subCategory3.setSubCategoryName("sub4");
            subCategory3.setIconName("home");

            SubCategory subCategory4 = new SubCategory();
            subCategory4.setSubCategoryName("sub5");
            subCategory4.setIconName("home");
            //////////////////////undefined


            SubCategory subCategoryUndefined = new SubCategory();
            subCategoryUndefined.setSubCategoryName("نامشخص");
            subCategoryUndefined.setIconName("question");
            subCategoryUndefined.setTag(StringUtil.UNDEFINED_TAG);
            subCategoryUndefined.setCategoryId(asyncCategoryDao.getUnDefinedCategory().getCategoryId());
            asyncSubCategoryDao.insert(subCategoryUndefined);

            //////////////////////undefined
            List<Category> cats = asyncCategoryDao.getAll();
            for (Category item : cats) {
                if (item.getTag() != null && item.getTag().equals(UNDEFINED_TAG)) {
                    continue;
                }
                subCategory1.setCategoryId(item.getCategoryId());
                subCategory2.setCategoryId(item.getCategoryId());
                subCategory3.setCategoryId(item.getCategoryId());
                subCategory4.setCategoryId(item.getCategoryId());

                asyncSubCategoryDao.insert(subCategory1);
                asyncSubCategoryDao.insert(subCategory2);
                asyncSubCategoryDao.insert(subCategory3);
                asyncSubCategoryDao.insert(subCategory4);
            }

            List<SubCategory> subcat = asyncSubCategoryDao.getAll();
            for (int i = 0; i < 20; i++) {
                Transaction transaction = new Transaction();
                Byte[] type = {(byte) 1, (byte) 2};
                SubCategory s = subcat.get((int) (Math.random() * subcat.size()));
                transaction.setAmount(new BigDecimal(Math.floor(Math.random() * 100) * 1000));
                transaction.setDescription("Some Description");
                transaction.setSubCategoryType(s.getSubCategoryId());
                transaction.setTransactionType(type[(int) Math.round(Math.random())]);
                transaction.setTransactionTime("2000");
                transaction.setTransactionDate("13970101");
                Log.d("Transactions", transaction.toString());
                asyncTransactionDao.insert(transaction);
            }

            return null;
        }
    }

}