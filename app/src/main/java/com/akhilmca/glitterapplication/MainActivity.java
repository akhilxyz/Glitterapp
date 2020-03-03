package com.akhilmca.glitterapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.akhilmca.glitterapplication.bottom_bar.HomeFragment;
import com.akhilmca.glitterapplication.bottom_bar.NotificationFragment;
import com.akhilmca.glitterapplication.bottom_bar.ProfileFragment;
import com.akhilmca.glitterapplication.bottom_bar.SearchFragment;
import com.akhilmca.glitterapplication.bottom_bar.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView btmnav = findViewById(R.id.bottom_nav);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new HomeFragment()).commit();
        }


        btmnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.upload:
                        fragment = new UploadFragment();
                        break;


                    case R.id.notification:
                        fragment = new NotificationFragment();
                        break;


                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,fragment).commit();

                    return true;
            }
        });


    }
}
