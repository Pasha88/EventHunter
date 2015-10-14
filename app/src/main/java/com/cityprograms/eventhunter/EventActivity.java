package com.cityprograms.eventhunter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventActivity extends AppCompatActivity {

    EditText userName, userComment;
    TextView headText, scheduleText, addressText, descriptionText, distanceText, priceText, ratingText, reviewsText;
    ImageView imageView;
    RatingBar mBar;
    private String distance;

    public static String eventComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent fragmentIntent = getIntent();
        String eventId = fragmentIntent.getStringExtra("eventId");
        distance = fragmentIntent.getStringExtra("distance");
        eventComment = eventId;

        ViewPager viewPager = (ViewPager) findViewById(R.id.pagerEvent);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        initialize();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("objectId", eventId);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventsParseList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + eventsParseList.size() + " scores");

                    for (ParseObject parseObject : eventsParseList) {
                        String objectId = (String) parseObject.get("objectId");
                        String title = (String) parseObject.get("title");
                        String link = (String) parseObject.get("link");
                        String schedule = (String) parseObject.get("schedule");
                        String address = (String) parseObject.get("address");
                        String description = (String) parseObject.get("description");
                        String imageLink = (String) parseObject.get("imageLink");
                        String phoneNumber = (String) parseObject.get("phoneNumber");
                        String newStatus = (String) parseObject.get("new");
                        String eventType = (String) parseObject.get("eventType");

                        int price = (int) parseObject.get("price");
                        double rating = parseObject.getDouble("rating");
                        int reviewsNumber = (int) parseObject.get("reviewsNumber");
                        headText.setText(title);
                        scheduleText.setText(schedule);
                        addressText.setText(address);
                        descriptionText.setText(description);
                        distanceText.setText(distance);
                        priceText.setText(String.valueOf(price));
                        ratingText.setText(String.valueOf(rating));
                        reviewsText.setText(String.valueOf(reviewsNumber));

                        Picasso.with(getBaseContext()).load(imageLink).into(imageView);
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void initialize() {
        userName = (EditText) findViewById(R.id.editName);
        userComment = (EditText) findViewById(R.id.editComment);
        mBar = (RatingBar) findViewById(R.id.ratingBar);
        headText = (TextView) findViewById(R.id.textHead);
        scheduleText = (TextView) findViewById(R.id.textSchedule);
        addressText = (TextView) findViewById(R.id.textAddress);
        descriptionText = (TextView) findViewById(R.id.textDescription);
        imageView = (ImageView) findViewById(R.id.imageViewPicasso);
        distanceText = (TextView) findViewById(R.id.textDistance);
        priceText = (TextView) findViewById(R.id.textPrice);
        ratingText = (TextView) findViewById(R.id.textRating);
        reviewsText = (TextView) findViewById(R.id.textReviews);

        mBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

            }
        });
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {

                android.support.v4.app.Fragment fragmentList = new CommentsFragment();
                return  fragmentList;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return "Комментарии";
        }
    }

    public void pushCommentOnTheServer(View view) {
        doIt();
    }

    public void doIt() {
        String userNameString = userName.getText().toString();
        String commentString = userComment.getText().toString();
        float mark = mBar.getRating();

        final String userNameFinal = userNameString;
        final String commentFinal =  commentString;
        final int markFinal = (int) mark;

        final ParseObject reviews = new ParseObject("Reviews");

        reviews .saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                reviews.put("eventId", eventComment);
                reviews.put("userName", userNameFinal);
                reviews.put("comment", commentFinal);
                reviews.put("mark", markFinal);
                reviews.saveInBackground();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.pagerEvent);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
