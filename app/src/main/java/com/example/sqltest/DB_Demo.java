package com.example.sqltest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class DB_Demo {

    private List<String> data = new LinkedList<>();
    private List<JSONObject> jsonObject = new LinkedList<>();
    private  boolean loginFlag = false;
    private  boolean updateFlag = false;
    private Context context;
    private String msg;

    public DB_Demo(Context con){
        context = con;
        System.out.println("初始化数据库模型...MyDatabase");
    }

    public boolean register(String name,int sex, String birthday, String pswd) throws UnsupportedEncodingException {
        String encode_name = java.net.URLEncoder.encode(name,"utf-8");
        final String strUrl="http://10.253.6.251:8080/whale/test?param1=register&param2="+encode_name+"&param3="+pswd.trim()+"&param4="+sex+"&param5="+birthday;
        System.out.println("register执行命令："+strUrl);
        Thread thread = new Thread(){
            @Override
            public void run() {
                _updateUser(strUrl);
            }
        };
        try{
            thread.start();
            thread.join();
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        System.out.println("子线程结束");
        return updateFlag;
    }

    public String addFavorite(String userid,String pswd, String bookid){
        final String strUrl = "http://10.253.6.251:8080/whale/test?param1=addFavorite&param2="+userid+"&param3="+pswd+"&param4="+bookid;
        System.out.println("执行命令："+strUrl);
        Thread thread = new Thread(){
            @Override
            public void run() {
                _addFavorite(strUrl);
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

        return msg;
    }

    public boolean deleteFavorite(int id) {
        String message = id+"";
        final String strUrl="http://10.253.6.251:8080/whale/test?param1=deleteData&param2="+message;
        System.out.println("deleteFavorite执行命令："+strUrl);
        Thread thread = new Thread(){
            @Override
            public void run() {
                _updateUser(strUrl);
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
        return updateFlag;
    }


    public boolean updateUser(int userid,String pswd,String tagn, String value) throws UnsupportedEncodingException {
        String message = java.net.URLEncoder.encode(value,"utf-8");
        final String strUrl="http://10.253.6.251:8080/whale/test?param1=updateUser&param2="+userid+"&param3="+pswd.trim()+"&param4="+tagn+"&param5="+message;
        System.out.println("执行命令："+strUrl);
        Thread thread = new Thread(){
            @Override
            public void run() {
                _updateUser(strUrl);
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
        return updateFlag;
    }

    private void _addFavorite(String strUrl){
        msg = "false";
        URL url = null;
        try{
            url=new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String readLine = null;
            System.out.println("updateUser正在获取内容内容...");
            if((readLine=br.readLine())!=null) {
                in.close();
                System.out.println("获取内容："+readLine);

                msg = readLine;
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _updateUser(String strUrl){
        updateFlag = false;
        URL url = null;
        try{
            url=new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String readLine = null;
            System.out.println("updateUser正在获取内容内容...");
            if((readLine=br.readLine())!=null) {
                in.close();
                System.out.println("获取内容："+readLine);
                if(readLine.equals("false")) {
                    System.out.println("返回false");
                    updateFlag = false;
                }
                else {
                    System.out.println("返回true");
                    updateFlag = true;
                }
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  List<JSONObject>GetFavorite(int userid, String pswd) {
        final String strUrl = "http://10.253.6.251:8080/whale/test?param1=getFavorite&param2=" + userid + "&param3=" + pswd;
        _gettable(strUrl);
        return jsonObject;
    }

//    public List<JSONObject>getFavorite(int userid, String pswd){
//        final String strUrl = "http://10.253.6.251:8080/whale/test?param1=getFavorite&param2="+userid+"&param3="+pswd;
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                _gettable(strUrl);
//            }
//        };
//        try{
//            thread.start();
//            thread.join();
//        }catch (Exception e){
//            System.out.println("子线程错误！");
//        }
//        System.out.println("子线程结束");
//        System.out.println("全局变量："+data);
//        return jsonObject;
//    }

    public List<JSONObject>GetSearchBook(String colName, String value) {
        final String strUrl = "http://10.253.6.251:8080/whale/test?param1=getSearchBook&param2=" + colName + "&param3=" + value;
        _gettable(strUrl);
        return jsonObject;
    }


    //用于获取book
//    public List<JSONObject>getSearchBook(String colName, String value){
//        final String strUrl="http://10.253.6.251:8080/whale/test?param1=getSearchBook&param2="+colName+"&param3="+value;
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                _gettable(strUrl);
//            }
//        };
//        try{
//            thread.start();
//            thread.join();
//        }catch (Exception e){
//            System.out.println("子线程错误！");
//        }
//        System.out.println("子线程结束");
//        System.out.println("全局变量："+data);
//        return jsonObject;
//    }

    public List<JSONObject>Get_table(String sqlmode) {
        String strUrl = "http://10.253.6.251:8080/whale/test?" + sqlmode;
        _gettable(strUrl);
        return jsonObject;
    }
    // 用于获取recommend类
//    public List<JSONObject>get_table(String sqlmode){
//        final String strUrl="http://10.253.6.251:8080/whale/test?"+sqlmode;
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                _gettable(strUrl);
//            }
//        };
//        try{
//            thread.start();
//            thread.join();
//        }catch (Exception e){
//            System.out.println("子线程错误！");
//        }
//        System.out.println("子线程结束");
//        System.out.println("全局变量："+data);
//        return jsonObject;
//    }

    private void  _gettable(String strUrl){
        System.out.println("开始连接");
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
            Whale whale = new Whale();
            while((readLine=br.readLine())!=null) {
                readLine = readLine.substring(1, readLine.length() - 1);
                jsonObject = whale.String2Jsons(readLine);
            }
            in.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 用于获取history类
//    public List<String> get_data(String sqlmode){
//        final String mode = sqlmode;
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                _getdata(mode);
//            }
//        };
//        try{
//            thread.start();
//            thread.join();
//        }catch (Exception e){
//            System.out.println("子线程错误！");
//        }
//        System.out.println("子线程结束");
//        System.out.println("全局变量："+data);
//        return data;
//    }

//    private void _getdata(String mode) {
//        System.out.println("开始连接");
//        String strUrl="http://10.253.6.251:8080/whale/test?"+mode;
//        System.out.println(strUrl);
//        URL url = null;
//        try{
//            url=new URL(strUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
//            BufferedReader br = new BufferedReader(in);
//            String result = "";
//            String readLine = null;
//            System.out.println("开始解析内容...");
//            while((readLine=br.readLine())!=null){
//                result=readLine+"\n";
//                data.add(result);
//                System.out.println(readLine);
//            }
//            in.close();
//            urlConnection.disconnect();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean Get_user(String id, String pswd){
        System.out.println("开始获取用户...");
        _getuser(id,pswd);
        return loginFlag;
    }

//    public boolean get_user(String id, String pswd){
//        System.out.println("开始获取用户数据...");
//        final String loginId = id;
//        final String loginPswd = pswd;
//
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                _getuser(loginId,loginPswd);
//            }
//        };
//        try{
//            thread.start();
//            thread.join();
//        }catch (Exception e){
//            System.out.println("子线程错误！");
//        }
//        System.out.println("子线程结束");
//        return loginFlag;
//    }

    private void _getuser(String id, String pswd){
        loginFlag = false;
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
                for (int i=1;i<6;i++){
                    int  borrow = json.getInt("borrow"+i);
                    localdb.setBorrow(usertable,"borrow"+i+"",borrow,json.getInt("id"));
                    try{
                        String tag = json.getString("tag"+i);
                        localdb.setTag(usertable,"tag"+i+"",tag,json.getInt("id"));
                    }catch (Exception e){
                        System.out.println("tag"+i+"没有数据！");;
                        localdb.setTag(usertable,"tag"+i+"","",json.getInt("id"));
                    }
                }
                usertable.close();
                loginFlag = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}