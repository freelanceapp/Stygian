package com.infobite.stygian.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.infobite.stygian.R;
import com.infobite.stygian.database.DatabaseHandler;
import com.infobite.stygian.fragment.ShoppingFragment;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.Utility;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    Context ctx;
    ImageView back_iv;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FrameLayout viewPager;

    public DatabaseHandler databaseCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        initXml();
    }

    private void initXml() {
        ctx = this;
        back_iv = findViewById(R.id.iv_chekout_back);
        toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        locationPermission();
    }

    private void locationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
            }else {
                setFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFragment() {
        ShoppingFragment fragment = new ShoppingFragment(ctx);
        Utility.setFragment1(fragment, ctx, ConstantData.HOME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            setFragment();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
