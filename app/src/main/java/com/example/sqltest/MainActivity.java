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
import android.widget.EditText;
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

    private Button home_btn,library_btn,profile_btn,search_btn;
    private Button login_btn,change_profile_btn,change_tags_btn;
    private Button tag1,tag2,tag3,tag4,tag5;
    private ImageButton setting_btn;
    private TextView username,userid,usersex,userbirthday;

    private ImageButton history_btn,recommend_tody_btn,personal_btn,tips_btn;
    private ImageButton shelf_btn,favorite_btn,Newest_btn,Borrow_btn;


    private EditText input_search;

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
        init_libary();
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
        username.setText(json.getString("name"));
        userid.setText(json.getInt("id")+"");
        userbirthday.setText(json.getString("birthday"));
        if (json.getInt("sex")==0){
            mypic.setImageResource(R.drawable.girl);
            usersex.setText("女");
        }else if(json.getInt("sex")==1){
            mypic.setImageResource(R.drawable.boy);
            usersex.setText("男");
        }
        List<String> list = mydb.getTag(db);
        System.out.println(list);
        tag1.setText(list.get(0).equals("")?"+":list.get(0));
        tag2.setText(list.get(1).equals("")?"+":list.get(1));
        tag3.setText(list.get(2).equals("")?"+":list.get(2));
        tag4.setText(list.get(3).equals("")?"+":list.get(3));
        tag5.setText(list.get(4).equals("")?"+":list.get(4));
        db.close();
    }

    public void clear_ui(){
        username.setText("");
        userid.setText("");
        usersex.setText("");
        userbirthday.setText("");
        mypic.setImageResource(R.mipmap.ic_launcher_round);
        tag1.setText("+");
        tag2.setText("+");
        tag3.setText("+");
        tag4.setText("+");
        tag5.setText("+");
    }

    public void init_main() {
        home_btn = findViewById(R.id.main_homePage);
        library_btn = findViewById(R.id.main_library);
        profile_btn = findViewById(R.id.main_profile);
        home_btn.setOnClickListener(this);
        library_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);
        search_btn =findViewById(R.id.main_search);
        search_btn.setOnClickListener(this);
        input_search = findViewById(R.id.main_input_search);
    }

    public void init_prpfile(){
        login_btn = profile.findViewById(R.id.profile_login_btn);
        change_profile_btn = profile.findViewById(R.id.profile_change_profile_btn);
        change_tags_btn = profile.findViewById(R.id.profile_change_tag_btn);
        setting_btn = profile.findViewById(R.id.profile_setting_btn);

        tag1 = profile.findViewById(R.id.mytag1);
        tag2 = profile.findViewById(R.id.mytag2);
        tag3 = profile.findViewById(R.id.mytag3);
        tag4 = profile.findViewById(R.id.mytag4);
        tag5 = profile.findViewById(R.id.mytag5);

        login_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        change_profile_btn.setOnClickListener(this);
        change_tags_btn.setOnClickListener(this);
        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        tag3.setOnClickListener(this);
        tag4.setOnClickListener(this);
        tag5.setOnClickListener(this);

        mypic = profile.findViewById(R.id.mypic);
        username = profile.findViewById(R.id.myname);
        userid = profile.findViewById(R.id.myid);
        usersex = profile.findViewById(R.id.mysex);
        userbirthday = profile.findViewById(R.id.mybirthday);
    }

    public void init_recommend(){
        history_btn = homepage.findViewById(R.id.Rhistory);
        recommend_tody_btn = homepage.findViewById(R.id.RToday);
        personal_btn = homepage.findViewById(R.id.RMy);
        tips_btn = homepage.findViewById(R.id.RTip);
        history_btn.setOnClickListener(this);
        recommend_tody_btn.setOnClickListener(this);
        personal_btn.setOnClickListener(this);
        tips_btn.setOnClickListener(this);
    }

    public void init_libary(){
        shelf_btn = library.findViewById(R.id.LLibarary);
        favorite_btn = library.findViewById(R.id.LFavorites);
        Newest_btn = library.findViewById(R.id.LNew);
        Borrow_btn = library.findViewById(R.id.LBorrow);
        shelf_btn.setOnClickListener(this);
        favorite_btn.setOnClickListener(this);
        Newest_btn.setOnClickListener(this);
        Borrow_btn.setOnClickListener(this);
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
            case R.id.main_homePage:    // 点击推荐事件，跳转推荐页
                Toast.makeText(this, "HomePage", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_library:     // 点击书库事件，跳转书库页
                viewPager.setCurrentItem(1);
                Toast.makeText(this, "Library", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_profile:     // 点击我的页，跳转我的页
                viewPager.setCurrentItem(2);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.RToday:       //点金今日推荐
                Toast.makeText(this,"Recommend Today",Toast.LENGTH_SHORT).show();
                Intent recommend_today = new Intent(MainActivity.this,Recommend.class);
                recommend_today.putExtra("table","Recommend");
                startActivity(recommend_today);
                break;
            case R.id.Rhistory:     //点击历史上的今天
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                Intent history_page = new Intent(MainActivity.this,History.class);
                startActivity(history_page);
                break;
            case R.id.profile_login_btn:        //点击登录 / 登出按钮
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
            case R.id.profile_setting_btn:         //点击设置按钮，跳转公告页
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                Intent settingPage = new Intent(this,Recommend.class);
                settingPage.putExtra("table","annouce");
                startActivity(settingPage);
                break;
            case R.id.main_search:      //点击搜索按钮
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                Intent searchPage = new Intent(this,SearchPage.class);
                searchPage.putExtra("value",input_search.getText().toString());
                startActivity(searchPage);
                break;
            case R.id.LLibarary:
                Toast.makeText(this,"浏览书库",Toast.LENGTH_SHORT).show();
                Intent shelfPage = new Intent(this,Shelf.class);
                startActivity(shelfPage);
                break;
            case R.id.LFavorites:
                Toast.makeText(this,"收藏夹",Toast.LENGTH_SHORT).show();
                Intent favoritePage = new Intent(this,Favorite.class);
                startActivity(favoritePage);
                break;
            case R.id.mytag1:
                Intent selectTag1 = new Intent(this,SelectTag.class);
                selectTag1.putExtra("tag","tag1");
                startActivity(selectTag1);
                break;
            case R.id.mytag2:
                Intent selectTag2 = new Intent(this,SelectTag.class);
                selectTag2.putExtra("tag","tag2");
                startActivity(selectTag2);
                break;
            case R.id.mytag3:
                Intent selectTag3 = new Intent(this,SelectTag.class);
                selectTag3.putExtra("tag","tag3");
                startActivity(selectTag3);
                break;
            case R.id.mytag4:
                Intent selectTag4 = new Intent(this,SelectTag.class);
                selectTag4.putExtra("tag","tag4");
                startActivity(selectTag4);
                break;
            case R.id.mytag5:
                Intent selectTag5 = new Intent(this,SelectTag.class);
                selectTag5.putExtra("tag","tag5");
                startActivity(selectTag5);
                break;
            case R.id.profile_change_profile_btn:
                Toast.makeText(this,"修改个人信息",Toast.LENGTH_SHORT).show();
                Intent changeProfile = new Intent(this,ChangeProfile.class);
                startActivity(changeProfile);
                break;
            case R.id.LNew:
                Toast.makeText(this,"最近上新",Toast.LENGTH_SHORT).show();
                Intent newestPage = new Intent(this,Newest.class);
                startActivity(newestPage);
                break;
            case R.id.LBorrow:
                Toast.makeText(this,"我的借阅",Toast.LENGTH_SHORT).show();
                Intent borrowPage = new Intent(this,BorrowList.class);
                startActivity(borrowPage);
                break;
            case R.id.RMy:
                Toast.makeText(this,"私人推荐",Toast.LENGTH_SHORT).show();
                Intent personalPage = new Intent(this,Personal.class);
                startActivity(personalPage);
                break;
            case R.id.RTip:
                Toast.makeText(this,"小tip",Toast.LENGTH_SHORT).show();
                Intent tipPage = new Intent(this,Recommend.class);
                tipPage.putExtra("table","Tips");
                startActivity(tipPage);
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
