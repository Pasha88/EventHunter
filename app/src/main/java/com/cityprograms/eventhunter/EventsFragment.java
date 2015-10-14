package com.cityprograms.eventhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventsFragment extends Fragment {

    private String choseCriteria;
    private double latCheck;

    private ListView mListView;

    private List<Event> myEventsList;

    public EventsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_events, container, false);
        mListView = (ListView) fragmentView.findViewById(R.id.MyListView);

        MainActivity activity = (MainActivity) getActivity();
        choseCriteria = activity.getMyChoseCriteria();

        latCheck = activity.getMyLat();
        if(latCheck > 0) {
            dataQuery();
        } else {
            activity.makeGoodToast();
        }

        return fragmentView;
    }

    public void dataQuery() {
        myEventsList = new ArrayList<Event>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventsParseList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + eventsParseList.size() + " scores");

                    for (ParseObject parseObject : eventsParseList) {
                        String objectId = parseObject.getObjectId();
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

                        double longi = parseObject.getDouble("longi");
                        double lat = parseObject.getDouble("lat");

                       Event event = new Event(objectId, title, link, imageLink, phoneNumber, schedule, address, eventType, description, newStatus,  price, rating, reviewsNumber, longi, lat);
                       event.getDistance();

                       myEventsList.add(event);
                    }

                    switch (choseCriteria) {
                        case "rating":
                            if(Event.order.equalsIgnoreCase("up")) {
                                Collections.sort(myEventsList, Event.Comparators.RATING);
                                Event.order = "down";
                            } else {
                                Collections.sort(myEventsList, Event.Comparators.RATING);
                                Event.order = "up";
                            }
                       break;
                        case "distance":
                            if(Event.order.equalsIgnoreCase("up")) {
                                Collections.sort(myEventsList, Event.Comparators.DISTANCE);
                                Event.order = "down";
                            } else {
                                Collections.sort(myEventsList, Event.Comparators.DISTANCE);
                                Event.order = "up";
                            }
                        break;
                        case "price":
                            if(Event.order.equalsIgnoreCase("up")) {
                                Collections.sort(myEventsList, Event.Comparators.PRICE);
                                Event.order = "down";
                            } else {
                                Collections.sort(myEventsList, Event.Comparators.PRICE);
                                Event.order = "up";
                            }
                        break;
                        case "review":
                            if(Event.order.equalsIgnoreCase("up")) {
                                Collections.sort(myEventsList, Event.Comparators.REVIEW);
                                Event.order = "down";
                            } else {
                                Collections.sort(myEventsList, Event.Comparators.REVIEW);
                                Event.order = "up";
                            }
                         break;
                   }

                    mListView.setAdapter(new MyAdapter());

                } else {
                    Log.d("EventHunterLog", "Error: " + e.getMessage());
                }
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        public int getCount() {
            return myEventsList.size();
        }

        @Override
        public Object getItem(int position) {
            return myEventsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = getActivity().getLayoutInflater().inflate(R.layout.row, null);

            Event rowEvent = myEventsList.get(position);

           final String eventChose = myEventsList.get(position).getObjectId();
            final String distance = String.valueOf(myEventsList.get(position).getDistance());
            Log.d("MYLOGEVENT",distance);

            TextView textViewRow = (TextView) rowView.findViewById(R.id.textHead);
            textViewRow.setText(myEventsList.get(position).getTitle());
            textViewRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEvent(eventChose, distance);
                }
            });

            ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewPicasso);
            Picasso.with(getActivity()).load(rowEvent.getImageLink()).into(imageView);

            TextView textViewRow2 = (TextView) rowView.findViewById(R.id.textDistance);
            textViewRow2.setText(String.valueOf(myEventsList.get(position).getDistance()));

            TextView textViewRow3 = (TextView) rowView.findViewById(R.id.textPrice);
            textViewRow3.setText(String.valueOf(myEventsList.get(position).getPrice()));

            TextView textViewRow4 = (TextView) rowView.findViewById(R.id.textRating);
            textViewRow4.setText(String.valueOf(myEventsList.get(position).getRating()/10));

            TextView textViewRow5 = (TextView) rowView.findViewById(R.id.textReviews);
            textViewRow5.setText(String.valueOf(myEventsList.get(position).getReviewsnumber()));

            Button buttonEventType = (Button) rowView.findViewById(R.id.buttonEventType);
            Button buttonSchedule = (Button) rowView.findViewById(R.id.buttonSchedule);

            TextView textViewRow6 = (TextView) rowView.findViewById(R.id.textSchedule);
            textViewRow6.setText(String.valueOf(myEventsList.get(position).getSchedule()));

            String newStatus = myEventsList.get(position).getNewStatus();
            Button buttonNew = (Button) rowView.findViewById(R.id.buttonNew);

            if(newStatus.equalsIgnoreCase("new")) {
                buttonNew.setVisibility(View.VISIBLE);
            } else if(newStatus.equalsIgnoreCase("old")) {
                buttonNew.setVisibility(View.INVISIBLE);
            }

            return rowView;
        }
    }

    public void openEvent(String eventId, String distance) {
        Intent myIntent = new Intent(getActivity(), EventActivity.class);
        myIntent.putExtra("eventId", eventId);
        myIntent.putExtra("distance", distance);
        startActivity(myIntent);
    }





}
