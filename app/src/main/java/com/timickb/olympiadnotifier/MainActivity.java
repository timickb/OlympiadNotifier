package com.timickb.olympiadnotifier;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        this.setContentView(R.layout.fragment_main);
        setContentView(R.layout.activity_main);

        // Импорт настроек пользователя
        settings = getSharedPreferences("pref", MODE_PRIVATE);

        // Инициализация элементов навигации
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Отображаем нужный фрагмент в зависимости от настроек пользователя
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction;

        String startFragment = settings.getString("start_fragment", "main");
        if(startFragment.equals("current")) {
            CurrentEventsFragment newFragment = new CurrentEventsFragment();
            transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if(startFragment.equals("favourites")) {
            FavouritesFragment newFragment = new FavouritesFragment();
            transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else {
            MainFragment newFragment = new MainFragment();
            transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        // Обработка нажатия кнопки возврата в Android
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager mgr = getSupportFragmentManager();
        if (!drawer.isDrawerOpen(GravityCompat.START) && mgr.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            mgr.popBackStack();
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Обработка переходов по пунктам бокового меню
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            MainFragment newFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if (id == R.id.nav_settings) {
            SettingsFragment newFragment = new SettingsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if (id == R.id.nav_about) {
            InfoFragment newFragment = new InfoFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if(id == R.id.nav_current) {
            CurrentEventsFragment newFragment = new CurrentEventsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if(id == R.id.nav_fav) {
            FavouritesFragment newFragment = new FavouritesFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        } else if(id == R.id.nav_ref) {
            ReferenceFragment newFragment = new ReferenceFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment, newFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

}
