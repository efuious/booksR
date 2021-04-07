package com.example.sqltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginPage extends Activity implements View.OnClickListener {

    private EditText edit_username,edit_password;
    private Button login_btn,register_btn;

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
        this.register_btn = findViewById(R.id.login_register_btn);
        this.login_btn.setOnClickListener(this);
        this.register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        DB_Demo mydb  = new DB_Demo(this);
        if (view.getId() == R.id.login_login_btn){
            String user = edit_username.getText().toString();
            String pswd = edit_password.getText().toString();
            if(mydb.get_user(user,pswd)){
                Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                this.finish();
            }
            else{
                Toast.makeText(this,"登录失败！",Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.login_register_btn){
            Intent register = new Intent(LoginPage.this,Register.class);
            startActivity(register);
        }
    }
}
