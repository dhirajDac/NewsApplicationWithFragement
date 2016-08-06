package com.vodafone.com.newsapplicationwithfragement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.network.NewsApiServiceHandler;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiSourceReference;
import com.vodafone.com.newsapplicationwithfragement.objects.Source;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeHomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_home);
        viewPager=(ViewPager)findViewById(R.id.activity_swipe_view_pager);

        Call<NewsApiSourceReference> sourceReference= NewsApiServiceHandler.getNewsAPI().getSource();
        sourceReference.enqueue(new Callback<NewsApiSourceReference>() {
            @Override
            public void onResponse(Call<NewsApiSourceReference> call, Response<NewsApiSourceReference> response) {
                List<Source> allSource=response.body().getSources();
                CommonUsage.setAllNewsSources(allSource);
                ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),allSource);
                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onFailure(Call<NewsApiSourceReference> call, Throwable t) {
                Toast.makeText(SwipeHomeActivity.this, "Error in Swip HomeAPICall", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter
    {
        List<Source> allSources;

        public ViewPagerAdapter(FragmentManager fm, List<Source> allSources) {
            super(fm);
            this.allSources = allSources;
        }

        @Override
        public Fragment getItem(int position) {
            return ListOfArticlesFragement.generateFragement(position);
        }

        @Override
        public int getCount() {
            return (null == allSources) ? 0 : allSources.size();
        }
    }
}
