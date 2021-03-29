package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.sf.json.JSONObject;


public class BookDetail extends AppCompatActivity {

    private JSONObject json;

    TextView title,author,publish,company,booknum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        json = new Whale().String2Json(intent.getStringExtra("string"));

        init();
    }

    public void init(){
        title = findViewById(R.id.bd_title);
        author = findViewById(R.id.bd_author);
        company = findViewById(R.id.bd_company);
        publish = findViewById(R.id.bd_publish);
        booknum = findViewById(R.id.bd_booknum);

        title.setText(json.getString("title"));
        author.setText(json.getString("author"));
        company.setText(json.getString("company"));
        publish.setText(json.getString("publish"));
        booknum.setText(json.getString("booknum"));
    }
}