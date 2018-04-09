package com.canhuah.h5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;

public class OverrideUrlActivity extends AppCompatActivity {

    private AppWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (AppWebView) findViewById(R.id.webview);
        findViewById(R.id.ll).setVisibility(View.GONE);

        mWebView.setWebViewClient(new LrWebViewClient());
        mWebView.loadUrl("file:///android_asset/overrideUrl.html");

    }

    private static class  LrWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //url符合条件进行一些交互操作,否则直接加载url
            //这里可以定义一个正则,这里不定义了 就直接简单包含bridge=了
            if(url.contains("bridge=")){

                String[] split = URLDecoder.decode(url).split("bridge=");
                String json = split[1];

                BridgeTypeBean bridgeTypeBean = JsonUtils.json2Object(json, BridgeTypeBean.class);
                if(bridgeTypeBean==null){
                    return true;
                }

                String bridgeType = bridgeTypeBean.getBridgeType();

                //模拟根据bridgeType进行操作,这里只是吐司,你可以做自己的操作
                switch (bridgeType) {
                    case BridgeTypeBean.LOGIN:

                        ToastUtils.show(bridgeTypeBean.getMessage());

                        break;

                	default:
                		break;
                }

            } else {
                view.loadUrl(url);
            }
            //return false 不会拦截
            return true;
        }
    }
}
