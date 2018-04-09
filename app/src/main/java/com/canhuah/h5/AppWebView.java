/*
 * @version 1.0
 * @date 17-7-21 下午5:56
 * Copyright 杭州优谷数据网络科技有限公司   All Rights Reserved
 *  未经授权不得进行修改、复制、出售及商业使用
 */

package com.canhuah.h5;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class AppWebView extends WebView {
    public AppWebView(Context context) {
        this(context,null);
    }

    public AppWebView(Context context, AttributeSet attrs) {
        //this(context, attrs,0)直接这样调用的话低版本可能不显示
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle","attr","android"));
    }

    public AppWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //安全相关
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");

        WebSettings settings = getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setAppCacheEnabled(false); //设置H5的缓存打开,默认关闭
        settings.setDomStorageEnabled(true);//vue好像需要用到
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);

        String ua = settings.getUserAgentString();
        //添加userAgent,H5需要识别(可根据自己前端要求添加)
        settings.setUserAgentString(ua + " LRAPP/Android");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
    }


}