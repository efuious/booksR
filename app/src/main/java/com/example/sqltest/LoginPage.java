package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_username,edit_password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        init();
    }

    public void init(){
        this.edit_username = findViewById(R.id.login_username);
        this.edit_password = findViewById(R.id.login_password);
        this.login_btn = findViewById(R.id.login_login_btn);

        this.login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.login_login_btn){
            String user = edit_username.getText().toString();
            String pswd = edit_password.getText().toString();
            String str = user+'\n'+pswd;
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
    }
}
