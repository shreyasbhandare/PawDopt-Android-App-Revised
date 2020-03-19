package com.sbhandare.pawdopt.View;

import android.content.Intent;
import android.os.Bundle;

import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        SecurityUserRepository securityUserRepository = new SecurityUserRepository(getApplicationContext());

         //TODO: add condition here to check access_token expiry of user. If expired, call pawdopt service with refresh_token and get new access_token, save that and launch main activity

        if(securityUserRepository.getAllSecurityUsers() != null && !securityUserRepository.getAllSecurityUsers().isEmpty()){
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            BaseActivity.this.startActivity(intent);
        }else{
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            BaseActivity.this.startActivity(intent);
        }
    }
}
