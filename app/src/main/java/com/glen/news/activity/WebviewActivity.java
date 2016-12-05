package com.glen.news.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.glen.news.R;

/**
 * Created by baojiarui on 2016/12/1.
 */
public class WebviewActivity extends BaseActivity implements View.OnClickListener {

    private WebView webview;

    private String url;
    private String titleStr;
    private ProgressBar mProgressBar; //webView进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        super.onCreate(savedInstanceState);

        init();
    }

    @SuppressLint({ "JavascriptInterface", "NewApi", "SetJavaScriptEnabled" })
    private void init() {
        url = getIntent().getStringExtra("url");
        titleStr = getIntent().getStringExtra("title");

        if (url == null || url.length() == 0) {
            Toast.makeText(getApplicationContext(), "数据错误", Toast.LENGTH_SHORT).show();
            return;
        }

        findViewById(R.id.btn_back).setOnClickListener(this);
        mProgressBar = (ProgressBar)findViewById(R.id.myProgressBar);

        //获取页面中的title
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String titles = title;
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    mProgressBar.setVisibility(View.INVISIBLE);
                }else {
                    if(View.INVISIBLE==mProgressBar.getVisibility()){
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
        };

        webview = (WebView) findViewById(R.id.webview);
        webview.setWebChromeClient(wvcc);
        webview.loadUrl(url);
        //webview.addJavascriptInterface(this, "Koolearn");
        webview.setBackgroundColor(Color.parseColor("#00000000"));
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 设置js可以直接打开窗口，如window.open()，默认为false
        webview.getSettings().setJavaScriptEnabled(true);// 是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webview.getSettings().setSupportZoom(true);// 是否可以缩放，默认true
        webview.getSettings().setBuiltInZoomControls(false);// 是否显示缩放按钮，默认false
        webview.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放。大视图模式
        webview.getSettings().setLoadWithOverviewMode(true);// 和setUseWideViewPort(true)一起解决网页自适应问题
        webview.getSettings().setAppCacheEnabled(true);// 是否使用缓存
        webview.getSettings().setDomStorageEnabled(true);// DOM Storage
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setInitialScale(100);
        if (Build.VERSION.SDK_INT >= 21) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackbtnPressed();
        }

        return true;
    }

    /**
     * 返回键被点击
     */
    private void onBackbtnPressed(){
        if (webview != null && webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackbtnPressed();
                break;

            default:
                break;
        }
    }

}