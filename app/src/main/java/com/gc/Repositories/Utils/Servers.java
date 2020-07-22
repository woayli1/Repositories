package com.gc.Repositories.Utils;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Servers extends Activity {

    public static String readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

    public static ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
            throws JSONException {
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<>();
            map.put("cname", jsonObject.getString("cname"));
            map.put("names", jsonObject.getString("names"));
            map.put("pwd", jsonObject.getString("pwd"));
            map.put("num", jsonObject.getString("num"));
            list.add(map);
        }
        return list;
    }

//    private void resultJson() {
//        try {
//            allData = Analysis(readParse(url));
//            Iterator<HashMap<String, Object>> it = allData.iterator();
//            while (it.hasNext()) {
//                Map<String, Object> ma = it.next();
//                if ((Integer) ma.get("id") == id) {
//                    biaoTi.setText((String) ma.get("biaoTi"));
//                    yuanJia.setText((String) ma.get("yuanJia"));
//                    xianJia.setText((String) ma.get("xianJia"));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
