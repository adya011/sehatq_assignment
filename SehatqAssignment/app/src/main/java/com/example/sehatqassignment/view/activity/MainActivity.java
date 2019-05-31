package com.example.sehatqassignment.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.utility.SharedPrefManager;
import com.example.sehatqassignment.view.fragment.DashboardFragment;

public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isUserLoggedIn()) {
            gotoDash();

        } else {
            gotoLogin();
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoDash() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
