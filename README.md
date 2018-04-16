## WebView的一些基础设置

[点击这里-->基础设置](https://canhuah.com/about-webview.html)



## WebView的安全问题

```java
webView.removeJavascriptInterface("searchBoxJavaBridge_");
//js接口安全漏洞
webView.removeJavascriptInterface("accessibility");         webView.removeJavascriptInterface("accessibilityTraversal";
//保存用户密码
webView.getSettings().setSavePassword(false);
```

<!--more-->

除非 min API level >=17，请注意 addJavascriptInterface 的使用。
(在API level>=17时，允许 js 被调用的函数必须以@JavascriptInterface 进行注解，因此不受影响 )



## Webview的交互

- <a href="#1">拦截url</a>

- <a href="#2">addJavaScriptInterface</a>

  ​

下面主要展示addJavaScriptInterface

#### <a name="1">拦截url</a>

```java
//设置自定义WebViewClient,重写shouldOverrideUrlLoading
mWebView.setWebViewClient(new LrWebViewClient());
mWebView.loadUrl("file:///android_asset/overrideUrl.html");
```



```java
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
```

html点这里

##### return false和return ture的区别

网上有部分资料说return true代表在本WebView中打开链接，return false代表调用系统浏览器打开。其实设置了WebViewClient，就不会调用系统浏览器。 

- return true在打开新的url时WebView就不会再加载这个url了，所有处理都需要在WebView中操作，包含加载；

- return false，则系统就认为上层没有做处理，接下来还是会继续加载这个url的；默认return false。

  ​

  **所以如果需要拦截并根据情况不同有可能只处理不跳转或者继续跳转就return ture,否则可以直接return false**

#### <a name="2">addJavaScriptInterface</a>

```java
final AppBridge bridge = new AppBridge();
//lrBridgeJS要与js中window.xxx中的这个xxx相同
mWebView.addJavascriptInterface(bridge, "lrBridgeJS");
mWebView.setWebViewClient(new LrWebViewClient());   mWebView.loadUrl("file:///android_asset/jsCallJava.html");
```



```java
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
```



 assets目录下测试jsCallJava.html如下

```html
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <script type="text/javascript">
    function javacalljs() {
      document.getElementById("content").innerHTML +=
        "<br\>java调用了js无参函数";
    }

    function javacalljswithargs(arg) {
      document.getElementById("content").innerHTML +=
        ("<br\>java调用了js有参函数,参数为:" + arg);
    }
    </script>
    <style>
    a, p, button, div {
      font-size: 35px
    }

    </style>
</head>
<body>
<p>
    <!--//window.lrBridgeJS这个lrBridgeJS要和安卓中mWebView.addJavascriptInterface(bridge, "lrBridgeJS")后面这个参数相同;-->
    <!--//方法名要与bridge中方法名相同-->
    <button onclick="window.lrBridgeJS.toast()" type="button"> 点击调用java代码</button>
    <button onclick="window.lrBridgeJS.toast('hello world')" type="button"> 点击调用java代码并传递参数</button>

    <br/>

<div id="content">内容显示</div>
</p>
</body>
</html>
```

### 总结

- 拦截url

  ​

  - 优点:前端只需要实行一套就好了,安卓iOS可以通用 
  - 缺点:只能h5调用安卓,安卓暂时不能通过这个方式调用h5的方法

  ​

- addJavascriptInterface

  ​

  - 优点:安卓可以和js互相调用,而且方法也比较直观
  - 缺点:因为安卓和iOSwebview这个地方可能不一样,前端稍微麻烦一点

[demo--> github](https://github.com/canhuah/H5)



参考 https://blog.csdn.net/qq_24530405/article/details/52067474

