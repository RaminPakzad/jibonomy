package sadad.com.jibonomy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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

import sadad.com.jibonomy.fragments.TransactionFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                Fragment fragment;
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                switch (item.getItemId()) {
                    case R.id.add_transaction:
                       toolbar.setTitle("AddTransaciton");
                       loadFragment(new TransactionFragment());
                        break;
                    case R.id.wish_list:
                       toolbar.setTitle("Wishes");
                       loadFragment(new WishListFragment());
                        break;
                    case R.id.home:
                        toolbar.setTitle("Home");
                        loadFragment(new HomeFragment());
                        break;
                }

                return false;
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Update user data in navigation bar
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        View headerView = navigationView.getHeaderView(0);
        TextView fullName = (TextView) headerView.findViewById(R.id.user_full_name);
        TextView email = (TextView) headerView.findViewById(R.id.user_email);
        fullName.setText(prefs.getString("firstName","") + " " + prefs.getString("lastName",""));
        email.setText(prefs.getString("email",""));

        // Main action menu click handler
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.main_nav_home:
                        Log.d("Item Clicked","main_nav_wish_list");
                        break;
                    case R.id.main_nav_wish_list:
                        Log.d("Item Clicked","main_nav_wish_list");
                        break;
                    case R.id.main_nav_transaction_list:
                        Log.d("Item Clicked","main_nav_transaction_list");
                        break;
                    case R.id.main_nav_report:
                        Log.d("Item Clicked","main_nav_report");
                        break;
                    case R.id.main_nav_setting:
                        Log.d("Item Clicked","main_nav_setting");
                        Intent i = new Intent(getBaseContext(), SettingFragment.class);
                        startActivity(i);
                        break;
                }

                return false;
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
        fragmentTransaction.commit();


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
}
