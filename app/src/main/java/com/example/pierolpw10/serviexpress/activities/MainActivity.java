package com.example.pierolpw10.serviexpress.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pierolpw10.serviexpress.Fragments.HomeFragment;
import com.example.pierolpw10.serviexpress.Menu.FragmentDrawer;
import com.example.pierolpw10.serviexpress.R;

public class MainActivity extends AppCompatActivity {

    FrameLayout fl_home_container;
    DrawerLayout mDrawerLayout;
    FragmentDrawer drawerFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_home_container = (FrameLayout) findViewById(R.id.fl_home_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupNavDrawer();

        fragment = new HomeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container,fragment);
        fragmentTransaction.commit();
        mDrawerLayout.closeDrawers();
    }

    private void setupNavDrawer() {
        mDrawerToggle= new ActionBarDrawerToggle(this, mDrawerLayout,null, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setUp(mDrawerLayout,true);

        /*mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("DEFENSORIA DEL PUEBLO");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(isFormulario){
                    getSupportActionBar().setTitle("REGISTRO DE UN CASO");
                }else{
                    getSupportActionBar().setTitle("HISTORIAL DE EXPEDIENTES");
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });*/
    }
}
