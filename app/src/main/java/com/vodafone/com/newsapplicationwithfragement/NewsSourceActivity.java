package com.vodafone.com.newsapplicationwithfragement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.network.NewsApiServiceHandler;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiSourceReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSourceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source);
        recyclerView=(RecyclerView)findViewById(R.id.activity_main_news_source);
        //progressBar= (ProgressBar) findViewById(R.id.activity_main__source_progress);
        //progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<NewsApiSourceReference> newsApiSource= NewsApiServiceHandler.getNewsAPI().getSource();
        newsApiSource.enqueue(new Callback<NewsApiSourceReference>() {
            @Override
            public void onResponse(Call<NewsApiSourceReference> call, Response<NewsApiSourceReference> response)
            {
                NewsApiSourceReference body=response.body();
                CommonUsage.setAllNewsSources(body.getSources());
                NewsSourceAdapter newsAdapter=new NewsSourceAdapter(CommonUsage.getAllNewsSources());
                recyclerView.setAdapter(newsAdapter);
                Toast.makeText(NewsSourceActivity.this, "Response", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<NewsApiSourceReference> call, Throwable t) {

            }
        });
    }

    public static void start(Context context, int position)
    {
        Intent intent=new Intent(context,NewsSourceActivity.class);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }
}
