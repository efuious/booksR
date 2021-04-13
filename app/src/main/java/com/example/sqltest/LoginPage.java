package com.example.sqltest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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
        final DB_Demo mydb  = new DB_Demo(this);
        if (view.getId() == R.id.login_login_btn){
            final String user = edit_username.getText().toString();
            final String pswd = edit_password.getText().toString();
            final LoginPage t = this;
            final Object o = new Object();
//            if(mydb.get_user(user,pswd)){
//                Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
//                this.finish();
//            }
//            else{
//                Toast.makeText(this,"登录失败！",Toast.LENGTH_SHORT).show();
//            }

            Thread thread = new Thread(){
                @Override
                public void run() {
                    if(mydb.Get_user(user,pswd)){
                        t.finish();
                    }
                    else{
                        try {
                            Toast.makeText(t, "登录失败！", Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                            //解决在子线程中调用Toast的异常情况处理
                            Looper.prepare();
                            Toast.makeText(t, "登录失败！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                    synchronized (o){
                        System.out.println("等待中...");
                        o.notify();
                    }
                }
            };
            try{
                thread.start();
                synchronized (o){
                    o.wait(100);
                }
            }catch (Exception e){
                System.out.println("子线程错误！");
            }
        }
        else if(view.getId() == R.id.login_register_btn){
            Intent register = new Intent(LoginPage.this,Register.class);
            startActivity(register);
        }
    }
}
