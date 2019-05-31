package com.example.sehatqassignment.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.utility.MyUtils;
import com.example.sehatqassignment.view.activity.LoginActivity;
import com.facebook.login.LoginManager;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {
    @BindView(R.id.logout)
    LinearLayout logout;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.alertDialogYN(getContext(), "Are you sure you want to logout?", "Yes", "No",
                        new MyUtils.DialogYN() {
                            @Override
                            public void positive() {
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }

                            @Override
                            public void negative() {

                            }
                        });
            }
        });
    }
}
