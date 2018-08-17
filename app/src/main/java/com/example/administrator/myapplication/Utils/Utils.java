package com.example.administrator.myapplication.Utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/6/29.
 */

public class Utils {

    private static Toast toast = null;


    public static void showToast(Context context, @StringRes int resId) {
        cancelToast();
        toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        //位置
        toast.setGravity(Gravity.TOP, Gravity.CENTER, 175);
        toast.show();
    }

    public static void showToast(Context context, String content) {
        cancelToast();
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        //位置
        toast.setGravity(Gravity.TOP, Gravity.CENTER, 175);
        toast.show();
    }

    /**
     * 取消显示Toast
     */
    public static void cancelToast() {
        if (toast != null)
            toast.cancel();
    }
}
