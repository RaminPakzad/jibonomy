package sadad.com.jibonomy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sadad.com.jibonomy.biz.dto.AccountsRequest;
import sadad.com.jibonomy.biz.dto.Result;
import sadad.com.jibonomy.dao.CategoryDao;
import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.dao.SubCategoryDao;
import sadad.com.jibonomy.dao.TransactionDao;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.fragments.CategoryListFragment;
import sadad.com.jibonomy.fragments.MerchantListFragment;
import sadad.com.jibonomy.fragments.TransactionFragment;
import sadad.com.jibonomy.services.APIService;
import sadad.com.jibonomy.utils.ApiUtils;
import sadad.com.jibonomy.utils.StringUtil;

import static sadad.com.jibonomy.utils.StringUtil.UNDEFINED_TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        insertDate(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.add_transaction:
                        toolbar.setTitle("تراکنش");
                        loadFragment(new TransactionFragment());
                        break;
                    case R.id.wish_list:
                        toolbar.setTitle("آرزوها");
                        loadFragment(new WishListFragment());
                        break;
                    case R.id.home:
                        toolbar.setTitle("خانه");
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.category:
                        toolbar.setTitle("طبقه بندی");
                        loadFragment(new CategoryListFragment());
                        break;
                    /*case R.id.dashboard:
                        toolbar.setTitle("گزارشات");
                        loadFragment(new DashboardFragment());*/
                    case R.id.merchants:
                        toolbar.setTitle("فروشگاه ها");
                        loadFragment(new MerchantListFragment());
                        break;
                }
                return false;
            }
        });


        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Update user data in navigation bar
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        View headerView = navigationView.getHeaderView(0);
        TextView fullName = (TextView) headerView.findViewById(R.id.user_full_name);
        TextView email = (TextView) headerView.findViewById(R.id.user_email);
        fullName.setText(prefs.getString("firstName", "") + " " + prefs.getString("lastName", ""));
        email.setText(prefs.getString("email", ""));

        // Main action menu click handler
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.main_nav_home:
                        toolbar.setTitle("خانه");
                        loadFragment(new HomeFragment());

                        break;
                    case R.id.main_nav_wish_list:
                        toolbar.setTitle("آرزوها");
                        loadFragment(new WishListFragment());
                        Log.d("Item Clicked", "main_nav_wish_list");
                        break;
                    case R.id.main_nav_transaction_list:
                        toolbar.setTitle("لیست تراکنش ها");
                        loadFragment(new AllTransactionsFragment());
                        Log.d("Item Clicked", "main_nav_transaction_list");
                        break;
                    case R.id.main_nav_report:
                        toolbar.setTitle("گزارشات");
                        loadFragment(new DashboardFragment());
                        Log.d("Item Clicked", "main_nav_report");
                        break;
                    case R.id.main_nav_setting:
                        Log.d("Item Clicked", "main_nav_setting");
                        Intent i = new Intent(getBaseContext(), SettingFragment.class);
                        startActivity(i);
                        break;

                }
                drawer.closeDrawers();
                return true;
            }
        });

        /*Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment, "HomeFragment");
        fragmentTransaction.commit();*/

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment, "HomeFragment");
        toolbar.setTitle("خانه");
        fragmentTransaction.commit();


        loadApiTransactions();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_transaction) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void insertDate(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        CategoryDao asyncCategoryDao = db.categoryDao();
        SubCategoryDao asyncSubCategoryDao = db.subCategoryDao();
        TransactionDao asyncTransactionDao = db.transactionDao();

        asyncCategoryDao.deleteAll();
        asyncSubCategoryDao.deleteAll();
        asyncTransactionDao.deleteAll();

        Category khorakCategory = new Category();
        khorakCategory.setCategoryId(1L);
        khorakCategory.setCategoryName("خوراک");
        khorakCategory.setBudget(new BigDecimal(5000000));
        khorakCategory.setIconName("ic_food_grey600_24dp");

        Category pooShaakCategory = new Category();
        pooShaakCategory.setCategoryId(2L);
        pooShaakCategory.setBudget(new BigDecimal(35000000));
        pooShaakCategory.setCategoryName("پوشاک");
        pooShaakCategory.setIconName("ic_tshirt_crew_grey600_24dp");


        Category transferCategory = new Category();
        transferCategory.setCategoryId(3L);
        transferCategory.setBudget(new BigDecimal(50000));
        transferCategory.setCategoryName("حمل و نقل");
        transferCategory.setIconName("ic_train_car_grey600_24dp");


        Category farhangyCategory = new Category();
        farhangyCategory.setCategoryId(4L);
        farhangyCategory.setCategoryName("فرهنگی");
        farhangyCategory.setBudget(new BigDecimal(300000));
        farhangyCategory.setIconName("ic_theater_grey600_24dp");

        Category darmaniCategory = new Category();
        darmaniCategory.setBudget(new BigDecimal(6000000));
        darmaniCategory.setCategoryName("درمانی");
        darmaniCategory.setIconName("ic_hospital_building_grey600_24dp");

////////////////////////undefined
        Category undefinedCategory = new Category();
        undefinedCategory.setBudget(new BigDecimal(1000000000));
        undefinedCategory.setCategoryName("نامشخص");
        undefinedCategory.setTag(UNDEFINED_TAG);
        undefinedCategory.setIconName("question");
////////////////////////undefined
        asyncCategoryDao.insert(khorakCategory);
        asyncCategoryDao.insert(pooShaakCategory);
        asyncCategoryDao.insert(transferCategory);
        asyncCategoryDao.insert(farhangyCategory);
        asyncCategoryDao.insert(darmaniCategory);
        asyncCategoryDao.insert(undefinedCategory);
//////////////////////////
        SubCategory subCategory1 = new SubCategory();
        subCategory1.setSubCategoryName("زیرگروه یک");
        subCategory1.setIconName("home");

        SubCategory subCategory2 = new SubCategory();
        subCategory2.setSubCategoryName("زیر گروه دو");
        subCategory2.setIconName("home");

        SubCategory subCategory3 = new SubCategory();
        subCategory3.setSubCategoryName("زیر گروه سه");
        subCategory3.setIconName("home");

        SubCategory subCategory4 = new SubCategory();
        subCategory4.setSubCategoryName("زیر گروه چهار");
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
        for (int i = 0; i < 10; i++) {
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
    }


    // get user transactions
    public void loadApiTransactions() {
        Log.d("API-CALL", "INIT");
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Log.d("API-CALL", token);
        token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJINGpXcmRDYl9sUHZRbmkwd3ZSOVQ2UkpMNlF2Mmt1V2NSakh4SjFrY2NRIn0.eyJqdGkiOiIyMjIwNGZlMy01ZWY0LTQ4NTEtOWNhNy1jYWI2YWFiMzgxNDkiLCJleHAiOjE1NDc3OTkwMzksIm5iZiI6MCwiaWF0IjoxNTQ3NzYzNzI0LCJpc3MiOiJodHRwOi8vcGZtLm15b3h5Z2VuLmlyL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6IjNjY2JhYjkyLTRiOTMtNGJmNC04MmJiLTBjY2Q1Yzg4Iiwic3ViIjoiNzhhNmMxMWMtNDg1MC00NjZjLTg0MTYtMzc1NWU4OTJmNjViIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiM2NjYmFiOTItNGI5My00YmY0LTgyYmItMGNjZDVjODgiLCJhdXRoX3RpbWUiOjE1NDc3NjMwMzksInNlc3Npb25fc3RhdGUiOiI4ZTA2ODkyOS04OTFkLTRmZTktYmY1Mi01NzliMWZmNDk1ZjgiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVzb3VyY2VfYWNjZXNzIjp7IlNCQWNjb3VudFRyYW5zYWN0aW9uIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtdHJ4Il19LCJTQkFjY291bnRCYWxhbmNlIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtYmFsYW5jZSJdfSwiU0JDdXN0b21lckFjY291bnRJbmZvIjp7InJvbGVzIjpbInN2Yy1tZ210LWN1c3RvbWVyLWFjY291bnQiXX19LCJzc24iOiIwNDUwMDkwOTAwIn0.U1gHkM6hjrdQxgcF8WU-vE1N_uT7wIlpwWgAG2QcyuAAPjQudCuIjvdLzpikNpg9mUQJoFCbtANjHGOiugw5sZBiAez1JG5_NK5SiznORwipHdtbVWS7U5voFQcR14vRfTpHVkGmF78wvYaBkY3VSyOEojbw9Epym9EV2IY7WzGKRYv8Y4kEeD8IdpqTdLT237lrb7pRdJBNkx5G-YNnB1P4kmIsnY_ba-7arsI3zKN1TICp4P22G1dVT63PZiXlWkYTD3K5ckYlHlCGYoZwQnzR8y_0oNTPxuPC_pzHB_nWZF1ZrLYdARLN1C9UEPH8Umf-ObwtZKJ7DkMwhCHXMg=";
        if (!token.equals("")) {
            Log.d("API-CALL", "APIService");
            APIService apiService = ApiUtils.getAPIService();
            AccountsRequest accountsRequest = new AccountsRequest();
            accountsRequest.setNationalIdentifier("0450090900");
            apiService.getAccounts(token).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Log.d("API-CALL", "200");
                    Log.d("API-CALL", response.headers().toString());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.d("API-CALL", "FAILED");
                }
            });
        }
    }

}
