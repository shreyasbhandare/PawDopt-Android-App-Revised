package com.sbhandare.pawdopt.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.sbhandare.pawdopt.Presenter.BaseActivityPresenter;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BaseActivity extends AppCompatActivity implements BaseActivityPresenter.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        BaseActivityPresenter baseActivityPresenter = new BaseActivityPresenter(this, getApplicationContext());

        if(baseActivityPresenter.isUserAlreadyLoggedIn()){
            baseActivityPresenter.refreshAccessToken();
        }else{
            loadLoginAcitivty();
        }
    }

    @Override
    public void loadMainActivity() {
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        BaseActivity.this.startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
    }

    @Override
    public void loadLoginAcitivty() {
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        BaseActivity.this.startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
    }
}
