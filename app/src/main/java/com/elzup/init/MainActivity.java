package com.elzup.init;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.elzup.init.fragments.MissionsFragment;
import com.elzup.init.managers.SessionStore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        SessionStore.setContext(this);
        if (!SessionStore.isLogin()) {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initFragment() {
        Fragment fragment;
        fragment = MissionsFragment.newInstance();
        fragmentManager.beginTransaction()
                .add(R.id.content_main, fragment)
                .commit();
    }

}
