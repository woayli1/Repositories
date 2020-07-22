package com.gc.Repositories.Utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager {

    private FileManager fm = new FileManager();
    private Servers se = new Servers();

    private static String TAG = "TimeManager";

    public String getInternetTime(int i) {
        String ret = null;
        switch (i) {
            case 1:
                ret = dealTimeURL1();
                if (ret == null) {
                    ret = dealTimeURL2();
                }
                if (ret == null) {
                    ret = dealTimeURL3();
                }
                break;
            case 2:
                ret = dealTimeURL2();
                if (ret == null) {
                    ret = dealTimeURL3();
                }
                if (ret == null) {
                    ret = dealTimeURL1();
                }
                break;
            case 3:
                ret = dealTimeURL3();
                if (ret == null) {
                    ret = dealTimeURL1();
                }
                if (ret == null) {
                    ret = dealTimeURL2();
                }
                break;
        }
        return ret;
    }

    private String dealTimeURL1() {
        String url = "http://quan.suning.com/getSysTime.do";
        String result;
        Date d;
        try {
            result = se.readParse(url).trim();
        } catch (Exception e) {
            Log.e(TAG, "获取时间戳 1 err 1:" + e);
            return null;
        }

        try {
            JSONObject data = JSON.parseObject(result);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sf.parse(data.get("sysTime2").toString());
        } catch (Exception e) {
            Log.e(TAG, "获取时间戳 1 err 2:" + e);
            return null;
        }
        return String.valueOf(d.getTime());
    }

    private String dealTimeURL2() {
        String url = "http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp";
        String result;
        JSONObject data;
        String ret;
        try {
            result = se.readParse(url).trim();
        } catch (Exception e) {
            Log.e(TAG, "获取时间戳 2 err 1:" + e);
            return null;
        }

        try {
            data = JSON.parseObject(result);
            ret = data.getJSONObject("data").get("t").toString();
        } catch (Exception e) {
            Log.e(TAG, "获取时间戳 2 err 2:" + e);
            return null;
        }
        return ret;
    }

    private String dealTimeURL3() {
        String url = "http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        String result;
        JSONObject data = new JSONObject();
        String ret;
        try {
            result = se.readParse(url).trim();
        } catch (Exception e) {
            Log.e(TAG, "获取时间戳 3 err 1:" + e);
            return null;
        }

        try {
            data = JSON.parseObject(result);
            if (data.get("success").toString().equals("1")) {
                ret = data.getJSONObject("result").get("timestamp").toString();
                ret = fm.rightaddZeroForNum(ret, 13);
            } else {
                Log.d(TAG, "获取时间戳 3 接口错误");
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "data=" + data);
            Log.e(TAG, "获取时间戳 3 err 2:" + e);
            return null;
        }
        return ret;
    }

    /* //日期转换为时间戳 */
    public String dayToStamp(String timers) {
        Date d = new Date();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fm.rightaddZeroForNum(String.valueOf(d.getTime()), 13);
    }

    /* //准确时间转换为时间戳 */
    public String timeToStamp(String timers) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.valueOf(sf.parse(timers.trim()).getTime());// 日期转换为时间戳
        } catch (ParseException e) {
            Log.e(TAG, "准确时间转时间戳 err :" + e);
            return null;
        }
    }

    /* //时间戳转换准确时间 */
    public String stampToTime(String stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.parseLong(stamp))); // 时间戳转换日期
    }


    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public Long getTimestamp() {
        return new Date().getTime();
    }

    public int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

}
