package com.vodafone.com.newsapplicationwithfragement.network;

import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiArticleReference;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiSourceReference;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 8/5/2016.
 */

public class NewsApiServiceHandler {
    private static final String API_KEY="1777b355ba5240fe8a5cf9f2545524ed";
    private static final String BASE_URL=" https://newsapi.org/v1/";

    private static NewsApiInterface newsInterface;
    public static NewsApiInterface getNewsAPI()
    {
        if(newsInterface==null)
        {
            Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            newsInterface=retrofit.create(NewsApiInterface.class);
        }
        return newsInterface;
    }

    public interface NewsApiInterface
    {
        @GET("articles?apiKey="+API_KEY)
        Call<NewsApiArticleReference> getArticle(@Query("source") String source,@Query("sortBy") String sortBy);

        @GET("sources")
        Call<NewsApiSourceReference> getSource();
    }
}
