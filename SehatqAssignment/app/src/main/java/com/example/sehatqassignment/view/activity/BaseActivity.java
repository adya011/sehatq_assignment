package com.example.sehatqassignment.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sehatqassignment.R;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ButterKnife.bind(this);
    }
}
