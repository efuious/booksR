package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class SelectTag extends AppCompatActivity {

    private ExpandableListView tagList;
    private String[][] childs;
    private String  tagn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);

        Intent intent = getIntent();
        tagn = intent.getStringExtra("tag");
        System.out.println("获取到tag："+tagn);
        Init();
    }

    public void getClick(int pos1,int pos2) throws UnsupportedEncodingException {
        System.out.println(childs[pos1][pos2]);
        String tagname = childs[pos1][pos2];
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        DB_Demo db_demo = new DB_Demo(this);
        int userid = mydb.get_userid(db);
        String pswd = mydb.get_pswd(db);
        if(db_demo.updateUser(userid,pswd,tagn,tagname)){
            mydb.setTag(db,tagn,tagname,userid);
        }
        db.close();
        finish();
    }

    public void Init(){
        DB_Demo mydb = new DB_Demo(this);
        List<JSONObject> list = new LinkedList<>();
        list = mydb.get_table("param1=getTag");

        childs = new String[list.size()][];
        for(int i=0;i<list.size();i++){
            childs[i] =list.get(i).getString("subTag").split(" ");// item;
        }
        String[] parents = new String[list.size()];
        for (int j=0;j<list.size();j++) {
            parents[j] = list.get(j).getString("mainTag");
        }

        ExpandableListviewAdapter adapter=new ExpandableListviewAdapter(this,parents,childs);

        tagList = findViewById(R.id.selectTaglist);
        tagList.setAdapter(adapter);

        tagList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                System.out.println("点击事件");
                try {
                    getClick(groupPosition,childPosition);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
    }
}