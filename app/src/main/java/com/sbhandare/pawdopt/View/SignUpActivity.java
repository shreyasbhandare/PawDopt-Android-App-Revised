package com.sbhandare.pawdopt.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sbhandare.pawdopt.R;

public class SignUpActivity extends AppCompatActivity {

    private TextView loginTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginTxt = findViewById(R.id.loginTxt);

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                SignUpActivity.this.startActivity(myIntent);
            }
        });
    }
}
