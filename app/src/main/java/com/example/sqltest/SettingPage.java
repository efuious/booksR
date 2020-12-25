package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

public class SettingPage extends AppCompatActivity implements View.OnClickListener {

    EditText ip;
    Button save_btn;

    private MyDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        init();
    }

    public void init(){
        ip = findViewById(R.id.setting_ip);
        save_btn = findViewById(R.id.setting_save_btn);
        save_btn.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mydb = (MyDatabase) bundle.getSerializable("db");

        System.out.println("ip 是: "+mydb.ip);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setting_save_btn:
                String newIP = ip.getText().toString();
                SharedPreferences pref = getSharedPreferences("IPADDR", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ipaddr",newIP);
                editor.apply();
                mydb.setMyIp(newIP);
                Connection conn = mydb.login();
                if (conn == null){
                    System.out.println("连接失败！");
                }
                else{
                    System.out.println("连接成功！");
                }
                Toast.makeText(this,"已更新设置: "+ip.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
