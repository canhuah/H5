/*
 * @version 1.0
 * @date 17-7-21 下午5:56
 * Copyright 杭州优谷数据网络科技有限公司   All Rights Reserved
 *  未经授权不得进行修改、复制、出售及商业使用
 */

package com.canhuah.h5;

import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast = null;

    public static void show(String msg) {
        if(TextUtils.isEmpty(msg)){
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(AppContext.getInstance().getBaseContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

}
