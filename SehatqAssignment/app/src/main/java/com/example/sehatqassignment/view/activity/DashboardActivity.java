package com.example.sehatqassignment.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.view.fragment.DashboardFragment;
import com.example.sehatqassignment.view.fragment.NewsFragment;
import com.example.sehatqassignment.view.fragment.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.bot_nav)
    BottomNavigationView nav;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        nav = findViewById(R.id.bot_nav);

        initToolbar();
        init();
        initNav();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void init() {
        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, new DashboardFragment());
        ft.commit();*/

        RelativeLayout searchBar = toolbar.findViewById(R.id.lay_search);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearch();
            }
        });
    }

    private void gotoSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void initNav() {
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = DashboardFragment.newInstance();

                switch (item.getItemId()) {
                    case R.id.action_item1:
                        selectedFrag = new DashboardFragment();
                        break;

                    case R.id.action_item2:
                        selectedFrag = new NewsFragment();
                        break;

                    case R.id.action_item3:
                        selectedFrag = new SettingsFragment();
                        break;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout, selectedFrag);
                ft.commit();

                return true;
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, new DashboardFragment());
        ft.commit();
    }
}
