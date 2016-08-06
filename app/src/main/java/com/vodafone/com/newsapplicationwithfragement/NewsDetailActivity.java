package com.vodafone.com.newsapplicationwithfragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.objects.Article;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;

/**
 * Created by admin on 8/4/2016.
 */

public class NewsDetailActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    private static final  int DEFAULT_POSITION=-1;
    private static final String KEY_POSITION="position";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        webView=(WebView)findViewById(R.id.news_detail_View);
        progressBar=(ProgressBar)findViewById(R.id.news_detail_progress);

        int position=getIntent().getIntExtra(KEY_POSITION,DEFAULT_POSITION);
        Article selectedObject= CommonUsage.getArticles().get(position);

        loadWebViewForNewObject(selectedObject);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadWebViewForNewObject(Article newsObject)
    {
        getSupportActionBar().setTitle(newsObject.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //To Enable Javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.loadUrl(newsObject.getUrl());
    }
    public static void start(Context context, int position)
    {
        Intent intent=new Intent(context,NewsDetailActivity.class);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }


}
