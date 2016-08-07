package com.vodafone.com.newsapplicationwithfragement;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vodafone.com.myapplication.R;
import com.vodafone.com.newsapplicationwithfragement.network.NewsApiServiceHandler;
import com.vodafone.com.newsapplicationwithfragement.objects.CommonUsage;
import com.vodafone.com.newsapplicationwithfragement.objects.NewsApiSourceReference;
import com.vodafone.com.newsapplicationwithfragement.objects.Source;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SwipeHomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_home);
        viewPager=(ViewPager)findViewById(R.id.activity_swipe_view_pager);
        NetworkAsyncTask myTask=new NetworkAsyncTask();
        myTask.execute();
     /*   Call<NewsApiSourceReference> sourceReference= NewsApiServiceHandler.getNewsAPI().getSource();
        try {
            Response<NewsApiSourceReference> response=  sourceReference.execute();
            List<Source> allSource=response.body().getSources();
            CommonUsage.setAllNewsSources(allSource);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), CommonUsage.getAllNewsSources());
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(8);


        } catch (IOException e) {
            e.printStackTrace();
        }*/
      /*  sourceReference.enqueue(new Callback<NewsApiSourceReference>() {
            @Override
            public void onResponse(Call<NewsApiSourceReference> call, Response<NewsApiSourceReference> response) {
                List<Source> allSource = response.body().getSources();
                CommonUsage.setAllNewsSources(allSource);

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                      //  Log.i(TAG,"OnPageScroll "+position);

                    }

                    @Override
                    public void onPageSelected(int position) {
                       // Log.i(TAG,"OnPageSelected "+position);
                        saveInPreference(position);

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                       // Log.i(TAG,"OnPageScrollStateChanged "+position);

                    }
                });
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), CommonUsage.getAllNewsSources());
                viewPager.setAdapter(viewPagerAdapter);
                viewPager.setOffscreenPageLimit(8);

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(SwipeHomeActivity.this);
                int position=sharedPreferences.getInt(KEY_POSITION,DEFAULT_POSITION);
                if(position!=0)
                {
                    viewPager.setCurrentItem(position);
                }
            }



            @Override
            public void onFailure(Call<NewsApiSourceReference> call, Throwable t) {
                Toast.makeText(SwipeHomeActivity.this, "Error in Swip HomeAPICall", Toast.LENGTH_SHORT).show();
            }
        });*/




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

        @Override
        public CharSequence getPageTitle(int position) {
            String title=CommonUsage.getAllNewsSources().get(position).getName();
            return title;
        }
    }

    public static final String KEY_POSITION="storedposition";
    public static final int DEFAULT_POSITION=0;
    public void saveInPreference(int position)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_POSITION,position);
        editor.commit();
    }


    public class NetworkAsyncTask extends AsyncTask<Void,Void,NewsApiSourceReference>
    {

        @Override
        protected NewsApiSourceReference doInBackground(Void... voids) {
            Call<NewsApiSourceReference> sourceReference= NewsApiServiceHandler.getNewsAPI().getSource();
            Response<NewsApiSourceReference> response;
            try {
                response = sourceReference.execute();
                return  response.body();
            }
           catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }



        @Override
        protected void onPostExecute(NewsApiSourceReference aBoolean) {
            List<Source> allSource = aBoolean.getSources();
            CommonUsage.setAllNewsSources(allSource);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //  Log.i(TAG,"OnPageScroll "+position);

                }

                @Override
                public void onPageSelected(int position) {
                    // Log.i(TAG,"OnPageSelected "+position);
                    saveInPreference(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // Log.i(TAG,"OnPageScrollStateChanged "+position);

                }
            });
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), CommonUsage.getAllNewsSources());
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(8);

            SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(SwipeHomeActivity.this);
            int position=sharedPreferences.getInt(KEY_POSITION,DEFAULT_POSITION);
            if(position!=0)
            {
                viewPager.setCurrentItem(position);
            }
        }



    }

}


