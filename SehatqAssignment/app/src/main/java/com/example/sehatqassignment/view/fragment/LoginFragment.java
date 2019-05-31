package com.example.sehatqassignment.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.utility.MyUtils;
import com.example.sehatqassignment.view.activity.DashboardActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.btn_login_fb)
    LoginButton btnLoginFb;

    @BindView(R.id.btn_login_google)
    SignInButton btnLoginGoogle;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;

    CallbackManager callbackManager;
    GoogleSignInClient googleSignInClient;
    static final String TAG = "mydbg_login_act";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.equals("sehatq") && password.equals("sehatq")) {
                    gotoDash();
                }else {
                    MyUtils.alertDialogOK(getContext(), "Username or password wrong");
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        btnLoginFb.setReadPermissions("email");
        btnLoginFb.setFragment(this);
        btnLoginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                gotoDash();
                Log.d(TAG, "login facebook success");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "login facebook cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "login facebook error");
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                gotoDash();

            } catch (ApiException e) {
                Log.d(TAG, "Exception: " + e);
            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void gotoDash() {
        sharedPrefManager.setUserLoggedIn(true);
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
