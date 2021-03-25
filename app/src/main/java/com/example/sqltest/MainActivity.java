package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private Button home_btn,library_btn,profile_btn;
    private Button login_btn,change_profile_btn,change_tags_btn;
    private ImageButton setting_btn;
    private TextView username,userid,usersex,userbirthday;

    private ImageButton history_btn,recommend_tody_btn;

    private ImageView mypic;

    private  View homepage,library,profile;

    MyPagerAdapter adapter;
    ViewPager viewPager;
    List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_main();
        init_pages();
        init_recommend();
        init_prpfile();
        renew_ui();
    }



    protected void onRestart() {
        super.onRestart();
        renew_ui();
    }

    public void renew_ui(){
        try {
            setup_ui();
            login_btn.setText("登出");
            set_buttons(true);
        } catch (JSONException e) {
            e.printStackTrace();
            login_btn.setText("登录");
            set_buttons(false);
            clear_ui();
        }
    }

    public void set_buttons(boolean status){
        change_profile_btn.setClickable(status);
        change_tags_btn.setClickable(status);
    }


    public void setup_ui() throws JSONException {
        System.out.println("更新ui...");
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        JSONObject json = mydb.getUser(db);
        System.out.println("用户名："+json.getString("name"));
        username.setText(json.getString("name"));
        userid.setText(json.getInt("id")+"");
        userbirthday.setText(json.getString("birthday"));
        if (json.getInt("sex")==0){
            mypic.setImageResource(R.drawable.girl);
            usersex.setText("女");
        }else if(json.getInt("sex")==1){
            mypic.setImageResource(R.drawable.boy);
            usersex.setText("男");
        }else{
            mypic.setImageResource(R.drawable.ic_launcher_background);
            usersex.setText("未知");
        }
    }

    public void clear_ui(){
        username.setText("");
        userid.setText("");
        usersex.setText("");
        userbirthday.setText("");
        mypic.setImageResource(R.mipmap.ic_launcher_round);
    }

    public void init_main() {
        home_btn = findViewById(R.id.main_homePage);
        library_btn = findViewById(R.id.main_library);
        profile_btn = findViewById(R.id.main_profile);
        home_btn.setOnClickListener(this);
        library_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);


    }

    public void init_prpfile(){
        login_btn = profile.findViewById(R.id.profile_login_btn);
        change_profile_btn = profile.findViewById(R.id.profile_change_profile_btn);
        change_tags_btn = profile.findViewById(R.id.profile_change_tag_btn);
        setting_btn = profile.findViewById(R.id.profile_setting_btn);

        login_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        change_profile_btn.setOnClickListener(this);
        change_tags_btn.setOnClickListener(this);

        mypic = profile.findViewById(R.id.mypic);
        username = profile.findViewById(R.id.myname);
        userid = profile.findViewById(R.id.myid);
        usersex = profile.findViewById(R.id.mysex);
        userbirthday = profile.findViewById(R.id.mybirthday);
    }

    public void init_recommend(){
        history_btn = homepage.findViewById(R.id.Rhistory);
        recommend_tody_btn = homepage.findViewById(R.id.RToday);
        history_btn.setOnClickListener(this);
        recommend_tody_btn.setOnClickListener(this);
    }

    public void init_pages(){
        homepage = View.inflate(MainActivity.this, R.layout.homepage, null);
        library = View.inflate(MainActivity.this, R.layout.library, null);
        profile = View.inflate(MainActivity.this, R.layout.profile, null);

        views = new ArrayList<View>();
        views.add((homepage));
        views.add(library);
        views.add(profile);

        adapter = new MyPagerAdapter(views);

        viewPager = findViewById(R.id.viewpage);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_homePage:
                Toast.makeText(this, "HomePage", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_library:
                viewPager.setCurrentItem(1);
                Toast.makeText(this, "Library", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_profile:
                viewPager.setCurrentItem(2);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.RToday:
                Toast.makeText(this,"Recommend Today",Toast.LENGTH_SHORT).show();
                Intent recommend_tody = new Intent(MainActivity.this,Recommend.class);
                startActivity(recommend_tody);
                break;
            case R.id.Rhistory:
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                Intent history_page = new Intent(MainActivity.this,History.class);
                startActivity(history_page);
                break;
            case R.id.profile_login_btn:
                Toast.makeText(this,"登录",Toast.LENGTH_SHORT).show();
                String mode = login_btn.getText().toString();
                System.out.println("模式："+mode);
                if(mode=="登录") {
                    Intent loginPage = new Intent(this, LoginPage.class);
                    startActivity(loginPage);
                }
                else{
                    System.out.println("删除用户数据");
                    MyDatabase mydb = new MyDatabase(this);
                    SQLiteDatabase sq = mydb.getWritableDatabase();
                    mydb.____delete_all(sq);
                    this.onRestart();
                }
                break;
            case R.id.profile_setting_btn:
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                Intent settingPage = new Intent(this,SettingPage.class);
                startActivity(settingPage);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
    }
}
