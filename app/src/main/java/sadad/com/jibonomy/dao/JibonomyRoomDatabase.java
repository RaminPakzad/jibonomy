package sadad.com.jibonomy.dao;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import sadad.com.jibonomy.entities.Wish;

@Database(entities = {Wish.class}, version = 1, exportSchema = false)
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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WishDao asyncWishDao;
        private final TransactionDao asyncTransactionDao;

        PopulateDbAsync(JibonomyRoomDatabase db) {
            asyncWishDao = db.wishDao();
            asyncTransactionDao = db.transactionDao();

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
            return null;
        }
    }

}