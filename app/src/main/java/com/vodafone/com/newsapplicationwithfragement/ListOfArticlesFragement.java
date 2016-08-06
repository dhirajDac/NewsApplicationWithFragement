package com.vodafone.com.newsapplicationwithfragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.network.NewsApiServiceHandler;
import com.vodafone.com.newsapplicationwithfragement.objects.Article;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiArticleReference;
import com.vodafone.com.newsapplicationwithfragement.objects.Source;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 8/6/2016.
 */

public class ListOfArticlesFragement extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private static final  int DEFAULT_POSITION=-1;
    private static final String KEY_POSITION="position";
    Source selectedSource;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position=getArguments().getInt(KEY_POSITION,DEFAULT_POSITION);
        selectedSource= CommonUsage.getAllNewsSources().get(position);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.activity_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.activity_main_newsItem);
        progressBar = (ProgressBar) view.findViewById(R.id.activity_main_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<NewsApiArticleReference> newsApiArticleReferenceCall= NewsApiServiceHandler.getNewsAPI().getArticle(selectedSource.getId(),selectedSource.getSortBysAvailable().get(0));
        newsApiArticleReferenceCall.enqueue(new Callback<NewsApiArticleReference>() {
            @Override
            public void onResponse(Call<NewsApiArticleReference> call, Response<NewsApiArticleReference> response)
            {
                NewsApiArticleReference body=response.body();
                List<Article> allArticles=body.getArticles();
                CommonUsage.setArticles(allArticles);

                NewsAdapter newsAdapter=new NewsAdapter(allArticles);
                recyclerView.setAdapter(newsAdapter);
            }


            @Override
            public void onFailure(Call<NewsApiArticleReference> call, Throwable t) {
                //Toast.makeText(SwipeHomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                // Toast.makeText(, "", Toast.LENGTH_SHORT).show();

            }
        });
       // progressBar.setVisibility(View.VISIBLE);
    }

    public static ListOfArticlesFragement generateFragement(int position)
    {
        ListOfArticlesFragement listOfArticleFramegement=new ListOfArticlesFragement();
        Bundle bundle=new Bundle();
        bundle.putInt(KEY_POSITION,position);
        listOfArticleFramegement.setArguments(bundle);
        return listOfArticleFramegement;
    }
}
