package com.elzup.init;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.elzup.init.models.SessionEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;
import com.elzup.init.utils.KeyUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILE_NAME = "preference";
    public static final String TAG = "LoginActivity";

    // UI references.
    private EditText mUserNameView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.user_name);

        Button mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(view -> {

            InitService initService = InitServiceGenerator.createService();
            String username = mUserNameView.getText().toString();
            final String password = KeyUtil.generateRandomCode();
            initService.login(username, password).enqueue(
                    new Callback<SessionEntity>() {
                        @Override
                        public void onResponse(Call<SessionEntity> call, Response<SessionEntity> response) {
                            SessionEntity sessionEntity = response.body();

                            SharedPreferences settings = getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("id", sessionEntity.getId());
                            editor.putString("username", sessionEntity.getUsername());
                            editor.putString("token", sessionEntity.getAccessToken());
                            editor.putString("password", sessionEntity.getAccessToken());

                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                            Log.i(TAG, String.valueOf(sessionEntity.getId()));
                            Log.i(TAG, sessionEntity.getUsername());
                            Log.i(TAG, sessionEntity.getAccessToken());
                            Log.i(TAG, sessionEntity.getTokenType());
                        }

                        @Override
                        public void onFailure(Call<SessionEntity> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

            attemptLogin();
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUserNameView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();

        // Check for a valid user name address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            mUserNameView.requestFocus();
            return;
        }
        showProgress(true);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

