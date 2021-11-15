package com.actofit.actofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.actofit.actofitdemo.databinding.ActivityRegUserBinding;

public class RegUserActivity extends AppCompatActivity {

    ActivityRegUserBinding activityRegUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_reg_user);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
    }

    private void initView() {

        activityRegUserBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(activityRegUserBinding.etUserName.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter User Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String contactNumber = activityRegUserBinding.etUserMob.getText().toString().trim();
                if (TextUtils.isEmpty(contactNumber) || contactNumber.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Enter Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                callNextIntent();
            }
        });
    }

    private void callNextIntent() {

        SharedPreferencesHelper.getInstance().save(activityRegUserBinding.etUserName.getText().toString().trim(), activityRegUserBinding.etUserMob.getText().toString());

        startActivity(new Intent(RegUserActivity.this, MainActivity.class));
    }
}