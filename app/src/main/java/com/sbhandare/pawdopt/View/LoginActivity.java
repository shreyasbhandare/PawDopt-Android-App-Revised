package com.sbhandare.pawdopt.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sbhandare.pawdopt.R;

public class LoginActivity extends AppCompatActivity {

    private TextView signUpTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpTxt = findViewById(R.id.signUpTxt);
        loginBtn = findViewById(R.id.loginBtn);

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }
}
