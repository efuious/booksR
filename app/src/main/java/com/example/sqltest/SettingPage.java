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

public class SettingPage extends AppCompatActivity {

    EditText ip;
    Button save_btn;

    private MyDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

//        init();
    }
//
//    public void init(){
//        System.out.println("进入");
//        ip = findViewById(R.id.setting_ip);
//        save_btn = findViewById(R.id.setting_save_btn);
//        save_btn.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.setting_save_btn:
//                String newIP = ip.getText().toString();
//                SharedPreferences pref = getSharedPreferences("IPADDR", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("ipaddr",newIP);
//                editor.apply();
//        }
//    }
}
