package com.example.sqltest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class ChangeProfile extends Activity implements View.OnClickListener {

    EditText name,birthday,sex,pswd;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        init();
    }

    public void init(){
        name = findViewById(R.id.cp_name);
        birthday = findViewById(R.id.cp_birthday);
        sex = findViewById(R.id.cp_sex);
        pswd = findViewById(R.id.cp_pswd);
        save_btn = findViewById(R.id.cp_save);
        save_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String new_name = name.getText().toString();
        String new_birthday = birthday.getText().toString();
        String new_sex = sex.getText().toString();
        String new_pswd = pswd.getText().toString();
        int get_sex = new Whale().checkSex(new_sex);
        if(get_sex>1&&!new_sex.isEmpty()){
            Toast.makeText(this,"请输入正确的性别！",Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(new_name + " " + new_birthday + " " + new_sex + " " + new_pswd);
        DB_Demo db_demo = new DB_Demo(this);
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        int id = mydb.get_userid(db);
        String ps = mydb.get_pswd(db);
        try {
            if (!new_name.isEmpty()) {      //更新用户名
               if( db_demo.updateUser(id, ps, "username", new_name)){
                   mydb.updateUser(db,id,"name",new_name);
               }else{
                   Toast.makeText(this,"用户名更新失败 ！请尝试输入其他用户名！",Toast.LENGTH_SHORT).show();
                   db.close();
                   return;
               }
            }
            if (!new_birthday.isEmpty()) {  //更新用户生日
                if(db_demo.updateUser(id, ps, "birthday", new_birthday)){
                    mydb.updateUser(db,id,"birthday",new_birthday);
                }else{
                    Toast.makeText(this,"生日更新失败 ！请检查输入！",Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
            }
            if (!new_sex.isEmpty()) {       //更新用户性别
                if(db_demo.updateUser(id, ps, "sex", get_sex+"")) {
                    mydb.updateUser(db, id, "sex", get_sex + "");
                }else{
                    Toast.makeText(this,"性别更新失败 ！请检查输入！",Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
            }
            if (!new_pswd.isEmpty()) {      //更新密码
                if(db_demo.updateUser(id, ps, "pswd", new_pswd)) {
                    mydb.updateUser(db, id, "pswd", new_pswd);
                }else{
                    Toast.makeText(this,"密码更新失败 ！请检查输入！",Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
            }
        } catch (UnsupportedEncodingException e) {
                Toast.makeText(this,"更新失败 ！请检查输入！",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                db.close();
                return;
        }
        Toast.makeText(this,"保存成功！",Toast.LENGTH_SHORT).show();
        db.close();
        finish();
    }
}