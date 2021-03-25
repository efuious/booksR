package com.example.sqltest;

import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Whale {

    public List<String> Parsing(String strs){
        String[] split = strs.split("\t");

        List<String> string = new LinkedList<>();
        for (int i=0;i<split.length;i++){
            string.add(split[i].trim());
            System.out.println("解析："+split[i].trim());
        }
//        System.out.println("数据："+string);
        return string;
    }

    public JSONObject String2Json(String string){
        System.out.println("转换字符串："+string);
        JSONObject jo =JSONObject.fromObject(string);
        return jo;
    }

    public List<JSONObject> String2Jsons(String string){
        String[] strs = string.split("\\{");
        List<JSONObject> json = new LinkedList<>();
        for (int i=1;i<strs.length;i++){
            String s2j = "{"+strs[i].replace("},","}").trim();
            System.out.println("转换字符串2："+s2j);
            JSONObject jo =JSONObject.fromObject(s2j);
            json.add(jo);
        }
        return json;
    }

}