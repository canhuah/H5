package com.canhuah.h5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JscallJavaActivity extends AppCompatActivity {

    private AppWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (AppWebView) findViewById(R.id.webview);
        final AppBridge bridge = new AppBridge();
        //lrBridgeJS要与js中
        mWebView.addJavascriptInterface(bridge, "lrBridgeJS");

        mWebView.setWebViewClient(new LrWebViewClient());
        mWebView.loadUrl("file:///android_asset/jsCallJava.html");
    }

    private static class LrWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }
    }

    //H5回调jsBridge
    private class AppBridge {

        //H5调Androdi无参函数
        @JavascriptInterface
        public void toast() {
            //注意用runOnUiThread,因为不在主线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show("js调用android无参参函数");
                }
            });

        }

        //H5调Androdi有参函数
        @JavascriptInterface
        public void toast(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show("js调用android有参函数,参数是:" + message);
                }
            });
        }
    }


    public void noparm(View view) {
        //也要注意线程问题
        if (mWebView != null) {
            //调用js无参函数
            // mWebView.loadUrl(getJsMethodName("javacalljswithargs"));
            mWebView.loadUrl("javascript:javacalljs()");
        }
    }


    public void parm(View view) {
        if (mWebView != null) {
            //调用js有参函数
            // mWebView.loadUrl(getJsMethodName("javacalljswithargs","hello world"));
            mWebView.loadUrl("javascript:javacalljswithargs('hello world')");
        }
    }


    private String getJsMethodName(String methodName) {
        return String.format("javascript:%s()", methodName);
    }


    private String getJsMethodName(String methodName, String args) {
        return String.format("javascript:%s('%s')", methodName, args);
    }


}
