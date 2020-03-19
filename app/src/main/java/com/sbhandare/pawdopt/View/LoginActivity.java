package com.sbhandare.pawdopt.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sbhandare.pawdopt.Presenter.LoginPresenter;
import com.sbhandare.pawdopt.R;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private EditText usernameEditTxt;
    private EditText passwordEditTxt;
    private TextView signUpTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginPresenter loginPresenter = new LoginPresenter(this, getApplicationContext());
        initUIElements();

        signUpTxt.setOnClickListener(view -> {
            Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            LoginActivity.this.startActivity(myIntent);
        });

        loginBtn.setOnClickListener(view -> {
            try {
                if(TextUtils.isEmpty(usernameEditTxt.getText()) && TextUtils.isEmpty(passwordEditTxt.getText())){
                    usernameEditTxt.setError( "Email is required!" );
                    passwordEditTxt.setError( "Password is required!" );
                }
                else if(TextUtils.isEmpty(usernameEditTxt.getText()))
                    usernameEditTxt.setError( "Email is required!" );
                else if(TextUtils.isEmpty(passwordEditTxt.getText()))
                    passwordEditTxt.setError( "Password is required!" );
                else {
                    String username = usernameEditTxt.getText().toString();
                    String password = passwordEditTxt.getText().toString();
                    loginPresenter.login(username, password);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUIElements(){
        signUpTxt = findViewById(R.id.signUpTxt);
        loginBtn = findViewById(R.id.loginBtn);
        usernameEditTxt = findViewById(R.id.emailEdit);
        passwordEditTxt = findViewById(R.id.passwordEdit);
    }

    @Override
    public void loadMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
