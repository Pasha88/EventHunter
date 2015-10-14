package com.cityprograms.eventhunter;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MyMapFragment extends SupportMapFragment  {

    GoogleMap googleMap;
    private static double mylongiS;
    private static double mylatS;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleMap = getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventsParseList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + eventsParseList.size() + " scores");

                    for (ParseObject parseObject : eventsParseList) {
                        String title = (String) parseObject.get("title");
                        double longi = (double) parseObject.getDouble("longi");
                        double lat = (double) parseObject.getDouble("lat");

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(lat, longi));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource((R.drawable.znak_festival)));
                        markerOptions.title(title);
                        googleMap.addMarker(markerOptions);
                    }

                } else {
                        Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    public GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
         public void onMyLocationChange(Location location) {
             mylatS = location.getLatitude();
             mylongiS  = location.getLongitude();
         }
    };

    public static double getMylongi () {
         return mylongiS;
    }

    public static double getMylat () {
         return mylatS;
    }
}
