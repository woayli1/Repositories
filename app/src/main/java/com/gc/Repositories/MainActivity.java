package com.gc.Repositories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private String message = "";
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*  返回Android唯一识别码
    * 注意：
    1. 厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c。
    2. 厂商定制系统的Bug：有些设备返回的值为null。
    3. 设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。
    * 链接：简书：https://www.jianshu.com/p/59440efa020c
    */
    /* start */
    public String getAndroid_id() {
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG, "deviceId=" + deviceId);
        return deviceId;
    }
    /* end */

    //展示消息
    /* start */
    public void showMessage(String meg) {
        if (message.equals(meg)) {
            return;
        }
        message = meg;
        Log.d(TAG, "弹出内容:" + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    /* end */


    //按两下返回键退出
    /* start */
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG, "返回键:按下了返回键");
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    /* end */


}