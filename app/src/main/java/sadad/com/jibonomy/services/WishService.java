package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.List;

import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.WishDao;
import sadad.com.jibonomy.entities.Wish;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class WishService {
    private Context context;
    private WishDao wishDao;

    public WishService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.wishDao = db.wishDao();
        this.context = context;
    }

    public void insert(Wish wish) {
        this.wishDao.insert(wish);
    }
    public void delete(Long wishId) {
        this.wishDao.delete(wishId);
    }

    public List<Wish> getWishes() {
        return wishDao.getAll();
    }

    public Wish getWish(Long wishId) {
        return wishDao.get(wishId);
    }

    public void deleteAll() {
        wishDao.deleteAll();
    }

}
