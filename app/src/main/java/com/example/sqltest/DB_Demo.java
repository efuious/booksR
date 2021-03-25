package com.example.sqltest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class DB_Demo {

    private List<String> data = new LinkedList<>();
    private List<JSONObject> jsonObject = new LinkedList<>();
    private  boolean loginFlag = false;
    private Context context;

    public DB_Demo(Context con){
        context = con;
        System.out.println("初始化数据库模型...MyDatabase");
    }

    public List<JSONObject>get_table(String sqlmode){
        final String mode = sqlmode;
        Thread thread = new Thread(){
            @Override
            public void run() {
                _gettable(mode);
            }
        };
        try{
            thread.start();
            thread.join();
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        System.out.println("子线程结束");
        System.out.println("全局变量："+data);
        return jsonObject;
    }

    private void  _gettable(String mode){
        System.out.println("开始连接");
        String strUrl="http://10.253.6.251:8080/whale/test?"+mode;
        System.out.println(strUrl);
        URL url = null;
        try{
            url=new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(in);
            List<JSONObject> result = new LinkedList<>();
            String readLine = null;
            System.out.println("_gettable开始解析内容...");
            while((readLine=br.readLine())!=null) {
                readLine = readLine.substring(1, readLine.length() - 1);
                jsonObject = new Whale().String2Jsons(readLine);
//                for(int i = 0;i<result.size();i++){
//                System.out.println(result.get(i).getString("title"));}
//                jsonObject.add(result);
            }
            in.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> get_data(String sqlmode){
        final String mode = sqlmode;
        Thread thread = new Thread(){
            @Override
            public void run() {
                _getdata(mode);
            }
        };
        try{
            thread.start();
            thread.join();
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        System.out.println("子线程结束");
        System.out.println("全局变量："+data);
        return data;
    }

    private void _getdata(String mode) {
        System.out.println("开始连接");
        String strUrl="http://10.253.6.251:8080/whale/test?"+mode;
        System.out.println(strUrl);
        URL url = null;
        try{
            url=new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String result = "";
            String readLine = null;
            System.out.println("开始解析内容...");
            while((readLine=br.readLine())!=null){
                result=readLine+"\n";
                data.add(result);
                System.out.println(readLine);
            }
            in.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean get_user(String id, String pswd){
        loginFlag = false;
        System.out.println("开始获取用户数据...");
        final String loginId = id;
        final String loginPswd = pswd;

        Thread thread = new Thread(){
            @Override
            public void run() {
                _getuser(loginId,loginPswd);
            }
        };
        try{
            thread.start();
            thread.join();
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        System.out.println("子线程结束");
        return loginFlag;
    }

    private void _getuser(String id, String pswd){
        System.out.println("开始连接");
        String strUrl="http://10.253.6.251:8080/whale/test?param1=login&param2="+id+"&param3="+pswd;
        System.out.println(strUrl);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String result = "";
            JSONObject json  = new JSONObject();
            result = br.readLine();
            if (result.isEmpty() || result.equals("{}") || result.equals("null")){
                System.out.println("认证失败！");
            }else {
                System.out.println("认证成功！:->"+result+"<-");
                json = new Whale().String2Json(result);
                MyDatabase localdb = new MyDatabase(this.context);
                SQLiteDatabase usertable = localdb.getWritableDatabase();
                localdb.add_data(usertable,json.getInt("id"),json.getString("username"),json.getInt("sex"),json.getString("birthday"),json.getString("pswd"));
                loginFlag = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}