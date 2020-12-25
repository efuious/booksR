package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase implements Serializable {
    String username = "username";
    String password = "password";
    String DBName   = "PyQt_db";
    String url_front = "jdbc:jtds:sqlserver://";
    String port = ":1433/";
    String ip = "";
    private Connection con;

    public MyDatabase(String ip){
        this.ip = ip;
        con = null;
        System.out.println("初始化数据库模型...MyDatabase");
    }

    public Connection renewIP(String ip){
        this.ip = ip;
        System.out.println("已更新IP...MyDatabase");
        return login();
    }

    public Connection login(){
        String url = this.url_front+this.ip+this.port+this.DBName;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            System.out.println("加载数据库驱动...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("加载数据库驱动完成！");
            System.out.println("开始连接数据库..."+this.ip);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("连接成功！");
            if(con == null){
                System.out.println("连接失败！");
            }
            else{
                System.out.println("连接成功！");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("类错误！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL错误！");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("其它错误！");
            e.printStackTrace();
        }
        return con;
    }

    public Boolean check_user(String username, String password){


        return Boolean.FALSE;
    }

    public void check_password(String username,String password){
        if (con != null){
            String sqlcmd = "Select * from usrelist where username = "+username;

            Statement statement = null;
            try {
                statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlcmd);
                System.out.println("密码: "+resultSet.getString("password"));
            } catch (SQLException e) {
                System.out.println("SQL错误！");
                e.printStackTrace();
            } catch (Exception e){
                System.out.println("未知错误！");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Connection is null");
        }

    }

    public void setMyIp(String ip){
        this.ip = ip;
        System.out.println("更新IP: "+ip);
    }

    public String get_data_from_table(String table){
        String data = "";
        Statement statement = null;
        if(con == null){
            System.out.println("获取链接失败！MyDatabase.get_data_from_table");
        }
        else{
            String sqlcmd = "Select * from "+table;
            try {
                statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlcmd);
                data = resultSet.getString(0);
            }
            catch (SQLException e){

            }
        }
        return data;
    }
}
