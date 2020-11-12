package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Demo extends AppCompatActivity implements View.OnClickListener{

    EditText ip;
    Button sqlbutton;
    TextView textView;

    String username = "username";
    String password = "password";
    String DBName   = "PyQt_db";
    String url_front = "jdbc:jtds:sqlserver://";
    String port = ":1433/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db__demo);
        init();
    }

    public void init(){
        ip = findViewById(R.id.edittext);
        sqlbutton = findViewById(R.id.sqlbutton);
        sqlbutton.setOnClickListener(this);
        textView = findViewById(R.id.textview);
    }

    public void login(){
        String ip_str = ip.getText().toString();
        String url = url_front+ip_str+port+DBName;

        Connection con = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            System.out.println("加载数据库驱动...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("加载数据库驱动完成！");

//            Toast.makeText(this,url,Toast.LENGTH_SHORT).show();

            con = DriverManager.getConnection(url, username, password);
            textView.setText("连接成功！");
            if(con == null){
                textView.setText("连接失败！");
            }
            else{
                textView.setText("连接成功！");
            }
        } catch (ClassNotFoundException e) {
            textView.setText("类错误！");
            e.printStackTrace();
        } catch (SQLException e) {
            textView.setText("SQL错误！");
            e.printStackTrace();
        } catch (Exception e){
            textView.setText("其它错误！");
            e.printStackTrace();
        }
        if (con != null){
            Statement statement = null;
            try {
                statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from history;");
                while (resultSet.next()){
                    textView.setText(resultSet.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            textView.setText("Connection is null");
        }
//        return con;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.sqlbutton){
            login();
        }
    }
}
