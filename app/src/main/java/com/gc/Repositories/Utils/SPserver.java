package com.gc.Repositories.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class SPserver {

    private Context context;

    public SPserver(Context context) {
        this.context = context;
    }

    public void setALLdata(JSONObject jsonObject) {
        SharedPreferences sp = context.getSharedPreferences("ALLdata", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("ALLdata", jsonObject.toString());
        et.apply();
    }

    public JSONObject getALLdata() {
        SharedPreferences sp = context.getSharedPreferences("ALLdata", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        return JSON.parseObject(sp.getString("ALLdata", null));
    }

    public void setupdateplaylist(JSONArray jsonArray) {
        SharedPreferences sp = context.getSharedPreferences("updateplaylist", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("updateplaylist", jsonArray.toString());
        et.apply();
    }

    public JSONArray getupdateplaylist() {
        SharedPreferences sp = context.getSharedPreferences("updateplaylist", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        return JSON.parseArray(sp.getString("updateplaylist", null));
    }

    public void setPlayList_no(JSONArray jsonArray) {
        SharedPreferences sp = context.getSharedPreferences("PlayList_no", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("PlayList_no", jsonArray.toString());
        et.apply();
    }

    public JSONArray getPlayList_no() {
        SharedPreferences sp = context.getSharedPreferences("PlayList_no", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        return JSON.parseArray(sp.getString("PlayList_no", null));
    }

    public void setPlayList(JSONArray jsonArray) {
        SharedPreferences sp = context.getSharedPreferences("playlist", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("playlist", jsonArray.toString());
        et.apply();
    }

    public JSONArray getPlayList() {
        SharedPreferences sp = context.getSharedPreferences("playlist", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        return JSON.parseArray(sp.getString("playlist", null));
    }

    public void setSoldOutIMG(JSONArray jsonArray) {
        SharedPreferences sp = context.getSharedPreferences("SoldOutIMG", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("SoldOutIMG", jsonArray.toString());
        et.apply();
    }

    public JSONArray getSoldOutIMG() {
        SharedPreferences sp = context.getSharedPreferences("SoldOutIMG", Context.MODE_MULTI_PROCESS | MODE_PRIVATE);
        return JSON.parseArray(sp.getString("SoldOutIMG", null));
    }
}
