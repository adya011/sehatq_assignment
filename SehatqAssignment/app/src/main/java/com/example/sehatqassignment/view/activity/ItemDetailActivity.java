package com.example.sehatqassignment.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.view.fragment.ItemDetailFragment;


public class ItemDetailActivity extends AppCompatActivity {
    static final String TAG = "mydbg_itemdet_act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("feed_id");

        initToolbar();

        Log.d(TAG, "item detail id: " + id);

        if (extras != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, ItemDetailFragment.newInstance(id));
            ft.commit();
        }
    }

    private void initToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feed Detail");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
