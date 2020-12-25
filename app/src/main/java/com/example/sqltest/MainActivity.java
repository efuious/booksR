package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private Button home_btn,login_btn;
    private Button library_btn;
    private Button profile_btn;
    private ImageButton setting_btn;

    private ImageButton history_btn;
    private MyDatabase mydb;
    private String ip;

    MyPagerAdapter adapter;
    ViewPager viewPager;
    List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_main();
        init_pages();
    }

    public void init_main() {
        home_btn = findViewById(R.id.main_homePage);
        library_btn = findViewById(R.id.main_library);
        profile_btn = findViewById(R.id.main_profile);
        home_btn.setOnClickListener(this);
        library_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);

        check_db();
    }

    public void check_db(){
        // 连接数据库
        System.out.println("开始初始化数据库模型...MainActivity");
        ip = getSharedPreferences("IPADDR",MODE_PRIVATE).getString("ipaddr","1.1.1.1");
        System.out.println("ip is: "+ip);
        mydb = new MyDatabase("10.1.1.103");
        System.out.println("开始获取数据库链接...MainActivity");
        Connection conn = mydb.login();
        System.out.println("验证数据库链接...");
        // 验证连接状态
        if(conn == null){
            System.out.println("错误！");
//            Toast.makeText(this,"连接服务器错误！",Toast.LENGTH_SHORT).show();
//            return false;
        }
        else{
            System.out.println("成功！");
//            Toast.makeText(this,"连接服务器成功！",Toast.LENGTH_SHORT).show();
//            return true;
        }
    }

    public void init_pages(){
        View homepage = View.inflate(MainActivity.this, R.layout.homepage, null);
        View library = View.inflate(MainActivity.this, R.layout.library, null);
        View profile = View.inflate(MainActivity.this, R.layout.profile, null);

        history_btn = homepage.findViewById(R.id.Rhistory);
        login_btn = profile.findViewById(R.id.profile_login_btn);
        setting_btn = profile.findViewById(R.id.profile_setting_btn);

        history_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);

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
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }
    @Override
    public void onClick(View v) {
        Bundle db = new Bundle();
        db.putSerializable("db",this.mydb);
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
            case R.id.Rhistory:
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                Intent history_page = new Intent(MainActivity.this,DB_Demo.class);
                history_page.putExtras(db);
                startActivity(history_page);
                break;
            case R.id.profile_login_btn:
                Toast.makeText(this,"登录",Toast.LENGTH_SHORT).show();
                Intent loginPage = new Intent(this, LoginPage.class);
                startActivity(loginPage);
                break;
            case R.id.profile_setting_btn:
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                Intent settingPage = new Intent(this,SettingPage.class);
                settingPage.putExtras(db);
                startActivity(settingPage);
        }
    }
}
