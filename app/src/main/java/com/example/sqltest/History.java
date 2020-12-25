package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class History extends AppCompatActivity {

    private  MyDatabase mydb;
    TextView textView;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        init();
    }

    public void init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mydb = (MyDatabase) bundle.getSerializable("db");
        textView = findViewById(R.id.history_textview);

        data = mydb.get_data_from_table("history");
        textView.setText(data);
    }
}
