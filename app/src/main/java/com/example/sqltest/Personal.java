package com.example.sqltest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Personal extends Activity implements View.OnClickListener {

    ImageButton book1, book2, book3, book4, book5,book6;
    TextView text1, text2, text3, text4, text5,text6;
    List<JSONObject> personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        init();
    }

    public void init() {
        personal = new LinkedList<>();
        init_book();
        init_ui();
        init_page();
    }

    @Override
    public void onClick(View view){
        Intent bookdetail = new Intent(Personal.this,BookDetail.class);
        switch (view.getId()){
            case R.id.p_book1:
                bookdetail.putExtra("string",personal.get(0).toString());
                startActivity(bookdetail);
                break;
            case R.id.p_book2:
                bookdetail.putExtra("string",personal.get(1).toString());
                startActivity(bookdetail);
                break;
            case R.id.p_book3:
                bookdetail.putExtra("string",personal.get(2).toString());
                startActivity(bookdetail);
                break;
            case R.id.p_book4:
                bookdetail.putExtra("string",personal.get(3).toString());
                startActivity(bookdetail);
                break;
            case R.id.p_book5:
                bookdetail.putExtra("string",personal.get(4).toString());
                startActivity(bookdetail);
                break;
            case R.id.p_book6:
                bookdetail.putExtra("string",personal.get(5).toString());
                startActivity(bookdetail);
                break;
        }
    }


    private void init_book(){
        DB_Demo db_demo = new DB_Demo(this);
        List<JSONObject> booklist = db_demo.getSearchBook("title", "");
        Collections.reverse(booklist);
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<String> taglist = mydb.getTag(db);

        int  count = 0;
        if(taglist == null || taglist.size()==0){      // 如果不存在taglist
            for(int i=0;i<6;i++){                      // 从booklist填充6本书
                personal.add(booklist.get(i));
            }
        }
        else{
            for(int i=0;i<5;i++){
                List<JSONObject> tagbook = db_demo.getSearchBook("tag2",taglist.get(i));
                if(tagbook.size()==0 || taglist.get(i).equals("")){
                    personal.add(booklist.get(count));
                    count++;
                    if(count>booklist.size()){count = 0;}
                } else{
                    System.out.println("获取到tag条数："+tagbook.size());
                    int number = new Random().nextInt(tagbook.size());
                    System.out.println("生成随机数："+number);
                    personal.add(tagbook.get(number));
                }
            }
            personal.add(booklist.get(count));
        }

    }
    private void init_ui(){
        book1 = findViewById(R.id.p_book1);
        book2 = findViewById(R.id.p_book2);
        book3 = findViewById(R.id.p_book3);
        book4 = findViewById(R.id.p_book4);
        book5 = findViewById(R.id.p_book5);
        book6 = findViewById(R.id.p_book6);

        book1.setOnClickListener(this);
        book2.setOnClickListener(this);
        book3.setOnClickListener(this);
        book4.setOnClickListener(this);
        book5.setOnClickListener(this);
        book6.setOnClickListener(this);

        text1 = findViewById(R.id.p_book1_name);
        text2 = findViewById(R.id.p_book2_name);
        text3 = findViewById(R.id.p_book3_name);
        text4 = findViewById(R.id.p_book4_name);
        text5 = findViewById(R.id.p_book5_name);
        text6 = findViewById(R.id.p_book6_name);
    }

    private void init_page(){
        text1.setText(personal.get(0).getString("title"));
        text2.setText(personal.get(1).getString("title"));
        text3.setText(personal.get(2).getString("title"));
        text4.setText(personal.get(3).getString("title"));
        text5.setText(personal.get(4).getString("title"));
        text6.setText(personal.get(5).getString("title"));
    }

}