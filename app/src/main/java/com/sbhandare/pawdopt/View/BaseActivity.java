package com.sbhandare.pawdopt.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.sbhandare.pawdopt.Presenter.BaseActivityPresenter;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class BaseActivity extends AppCompatActivity implements BaseActivityPresenter.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        BaseActivityPresenter baseActivityPresenter = new BaseActivityPresenter(this, getApplicationContext());
        baseActivityPresenter.getAccessToken();

        boolean hasLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!hasLocationPermission) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PawDoptUtil.REQUEST_LOCATION);
        }else
            loadMainActivity();
    }

    public void loadMainActivity() {
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        BaseActivity.this.startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PawDoptUtil.REQUEST_LOCATION) {
            loadMainActivity();
        }
    }
}
