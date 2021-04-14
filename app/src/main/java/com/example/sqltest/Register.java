package com.example.sqltest;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class Register extends Activity implements View.OnClickListener{
    EditText r_name,r_sex,r_birthday,r_pswd;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        init();
    }

    public void init(){
        r_name = findViewById(R.id.cp_name);
        r_sex = findViewById(R.id.cp_sex);
        r_birthday = findViewById(R.id.cp_birthday);
        r_pswd = findViewById(R.id.cp_pswd);
        register_btn = findViewById(R.id.cp_save);
        register_btn.setText("注册");
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        final String new_name = r_name.getText().toString();
        final String new_birthday = r_birthday.getText().toString();
        final String new_sex = r_sex.getText().toString();
        final String new_pswd = r_pswd.getText().toString();
        final int get_sex = new Whale().checkSex(new_sex);
        if(get_sex>1){
            Toast.makeText(this,"请输入正确的性别！",Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(new_name + " " + new_birthday + " " + new_sex + " " + new_pswd);
        final DB_Demo db_demo = new DB_Demo(this);

        if (!new_name.isEmpty()&&!new_birthday.isEmpty()&&!new_sex.isEmpty()&&!new_pswd.isEmpty()) {
            final Object o = new Object();
            final Context t = this;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        boolean flag = db_demo.Register(new_name, get_sex, new_birthday, new_pswd);
                        if (flag) {
                            System.out.println("注册成功！");
                            Toast.makeText(t, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            System.out.println("注册失败！");
                            Toast.makeText(t, "注册失败！请检查输入", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Looper.prepare();
                        Toast.makeText(t, "注册失败！请检查输入", Toast.LENGTH_SHORT).show();
                        Looper.loop();
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
    }
}