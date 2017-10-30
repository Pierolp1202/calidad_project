package com.example.pierolpw10.serviexpress.Menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.Activities.LoginActivity;
import com.example.pierolpw10.serviexpress.Fragments.AccountFragment;
import com.example.pierolpw10.serviexpress.Fragments.HelpFragment;
import com.example.pierolpw10.serviexpress.Fragments.HistorialFragment;
import com.example.pierolpw10.serviexpress.Fragments.HomeFragment;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FragmentDrawer extends Fragment {

    TextView tv_nombre;
    TextView tv_mail;
    RecyclerView drawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View view;
    private RecyclerView.LayoutManager layoutManager;

    PreferenceManager manager;

    User user;

    Fragment fragment = null;

    boolean isHome = true;

    public FragmentDrawer() {
    }

    private List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
//        data.add(new NavDrawerItem("Account",true,true, R.drawable.heart));
//        data.add(new NavDrawerItem("Home",true,true, R.drawable.casita));
//        data.add(new NavDrawerItem("Historial",true,true,R.drawable.video));
//        data.add(new NavDrawerItem("Help",true,true,R.drawable.video_3d));
        data.add(new NavDrawerItem("Account",true,true, 0));
        data.add(new NavDrawerItem("Home",true,true, 0));
        data.add(new NavDrawerItem("Historial",true,true,0));
        data.add(new NavDrawerItem("Help",true,true,0));
        data.add(new NavDrawerItem("Salir",true,true,0));
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);

        drawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
        tv_mail = (TextView) view.findViewById(R.id.tv_mail);

        manager = PreferenceManager.getInstance(getActivity());

        Gson gson = new Gson();
        user = gson.fromJson(manager.getPreferenceSession(), User.class);

        tv_nombre.setText(user.getNombres() + " " + user.getApellidos());
        tv_mail.setText(user.getMail());

        return view;
    }

    public void setUp(DrawerLayout drawerLayout, boolean home) {
        setupElements();
        isHome = home;
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void setupElements(){
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        drawerList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        drawerList.setLayoutManager(layoutManager);
        adapter = new NavigationDrawerAdapter(getContext(),getData());
        drawerList.setAdapter(adapter);

        adapter.setOnItemClickListener(new NavigationDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(getActivity() != null){
                    switch (position){
                        case 0:
                            fragment = new AccountFragment();
                            changeFragment();
                            break;
                        case 1:
                            fragment = new HomeFragment();
                            changeFragment();
                            break;
                        case 2:
                            fragment = new HistorialFragment();
                            changeFragment();
                            break;
                        case 3:
                            fragment = new HelpFragment();
                            changeFragment();
                            break;
                        case 4:
                            showDialog();

                    }

                }
            }
        });
    }

    private void changeFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container,fragment);
        fragmentTransaction.commit();
        mDrawerLayout.closeDrawers();
    }

    private void showDialog() {
        manager.setPrefenceSession("");
        Intent i = new Intent(getActivity(),LoginActivity.class);
        getActivity().startActivity(i);
        getActivity().finish();

//       final Dialog dialog = new Dialog(getActivity());
//
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        dialog.setContentView(R.layout.dialog_logout);
//
//        TextView dialog_si = (TextView)dialog.findViewById(R.id.dialog_si);
//        TextView dialog_no = (TextView)dialog.findViewById(R.id.dialog_no);
//
//        dialog_si.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                manager.logOut();
//                startActivity(new Intent(getActivity(), SplashActivity.class));
//                getActivity().finish();
//            }
//        });
//
//        dialog_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
    }
}