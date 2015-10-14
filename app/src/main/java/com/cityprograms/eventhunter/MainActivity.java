package com.cityprograms.eventhunter;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private List<Event> myEventsList;
    public static double mylat;
    public static double mylong;
    private String myChoseCriteria = "rating";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myChoseCriteria = "distance";
                mylat = MyMapFragment.getMylat();
                mylong = MyMapFragment.getMylongi();
                viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
            }
        }, 3500);


    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
       }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
           if(position == 0) {
              android.support.v4.app.Fragment fragmentList = new EventsFragment();
              return  fragmentList;
           } else {
               android.support.v4.app.Fragment fragmentMap = new MyMapFragment();
               return fragmentMap;
           }
        }

        @Override
        public int getCount() {
           return 2;
       }

        @Override
        public CharSequence getPageTitle(int position) {
           if(position == 0) {
               return "События";
           } else {
               return "Карта";
           }
        }
   }

    public void sortByDistance(View view) {
        myChoseCriteria = "distance";
        mylat = MyMapFragment.getMylat();
        mylong = MyMapFragment.getMylongi();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    public void sortByPrice(View view) {
        myChoseCriteria = "price";
        mylat = MyMapFragment.getMylat();
        mylong = MyMapFragment.getMylongi();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    public void sortByRating(View view) {
        myChoseCriteria = "rating";
        mylat = MyMapFragment.getMylat();
        mylong = MyMapFragment.getMylongi();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }


    public void sortByReviews(View view) {
        myChoseCriteria = "review";
        mylat = MyMapFragment.getMylat();
        mylong = MyMapFragment.getMylongi();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    public double getMyLat() {
        return mylat;
    }

    public double getMyLong() {
        return mylong;
    }

    public String getMyChoseCriteria() {
        return myChoseCriteria;
    }

    public void makeGoodToast() {
        Toast.makeText(getApplicationContext(), "Вычисляем дистанцию до мероприятий", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }





}
