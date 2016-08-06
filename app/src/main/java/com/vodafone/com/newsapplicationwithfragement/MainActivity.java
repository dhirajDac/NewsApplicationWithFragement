package com.vodafone.com.newsapplicationwithfragement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.NewsAdapter;
import com.vodafone.com.newsapplicationwithfragement.network.NewsApiServiceHandler;
import com.vodafone.com.newsapplicationwithfragement.objects.Article;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiArticleReference;
import com.vodafone.com.newsapplicationwithfragement.objects.Source;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private static final  int DEFAULT_POSITION=-1;
    private static final String KEY_POSITION="position";
    List<Article> allArticles=new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //// TODO: 8/3/2016  Make change
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_newsItem);
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int position=getIntent().getIntExtra(KEY_POSITION,DEFAULT_POSITION);
        Source source=CommonUsage.getAllNewsSources().get(position);
        Call<NewsApiArticleReference> newsApiArticleReferenceCall= NewsApiServiceHandler.getNewsAPI().getArticle(source.getId(),source.getSortBysAvailable().get(0));
        newsApiArticleReferenceCall.enqueue(new Callback<NewsApiArticleReference>() {
            @Override
            public void onResponse(Call<NewsApiArticleReference> call, Response<NewsApiArticleReference> response)
            {
                NewsApiArticleReference body=response.body();
                allArticles=body.getArticles();
                CommonUsage.setArticles(allArticles);

                NewsAdapter newsAdapter=new NewsAdapter(allArticles);
                recyclerView.setAdapter(newsAdapter);
                //NewsAdapter newsAdapter=new NewsAdapter(NewsObject.getNewsItems());

                //Toast.makeText(MainActivity.this, "Response", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<NewsApiArticleReference> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });





    }

    public static void start(Context context, int position)
    {
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

}
