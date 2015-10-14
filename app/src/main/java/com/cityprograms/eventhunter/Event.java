package com.cityprograms.eventhunter;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.util.Comparator;

public class Event implements Comparable<Event>{

    private String objectId;
    private String title;
    private String link;
    private String imageLink;
    private String phoneNumber;
    private String schedule;
    private String eventType;
    private String address;
    private String description;
    private String newStatus;
    private int price;
    private double rating;
    private int reviewsNumber;
    private double longi;
    private double lat;
    private double distance;

    public static String order = "up";

    public Event(String objectId, String title, String link, String imageLink, String phoneNumber, String schedule, String address, String eventType, String description, String newStatus, int price, double rating, int reviewsNumber, double longi, double lat) {
        this.objectId = objectId;
        this.title = title;
        this.link = link;
        this.imageLink = imageLink;
        this.phoneNumber = phoneNumber;
        this.schedule = schedule;
        this.address = address;
        this.eventType = eventType;
        this.description = description;
        this.newStatus = newStatus;
        this.price = price;
        this.rating = rating;
        this.reviewsNumber = reviewsNumber;
        this.longi = longi;
        this.lat = lat;
       // this.distance = distance;
    }


    public String getTitle() {
        return  title;
    }

    public String getLink() {
        return link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public double getLongi() {
        return longi;
    }

    public double getLat() {
        return lat;
    }

    public double getDistance() {
        distance = Math.sqrt((MainActivity.mylat-lat)*(MainActivity.mylat-lat)+(MainActivity.mylong-longi)*(MainActivity.mylong-longi))*100000;
        double finalValue = Math.round( distance * 100.0 ) / 100.0;
        this.distance = finalValue;
        return finalValue;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getSchedule() {
        return schedule;
    }


    public String getAddress() {
        return address;
    }


    public int getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewsnumber() {
        return reviewsNumber;
    }

    @Override
    public int compareTo(Event another) {
        int compareSalary = (int) ((Event) another).getRating();

        return (int) (compareSalary - this.rating);
    }

    public String getEventType() {
        return eventType;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public static class Comparators {

        public static Comparator<Event> RATING = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                int raiting = (int) ((Event) o1).getRating();

                if(order.equalsIgnoreCase("up")) {
                    return (int) (o2.rating - raiting);
                } else {
                    return (int) (raiting - o2.rating);
                }
            }
        };

        public static Comparator<Event> DISTANCE = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                int distance = (int) ((Event) o1).getDistance();

                if(order.equalsIgnoreCase("up")) {
                   return (int) (o2.distance-distance);
                } else {
                    return (int) (distance-o2.distance);
                }

            }
        };

        public static Comparator<Event> PRICE = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                int price = (int) ((Event) o1).getPrice();

                if(order.equalsIgnoreCase("up")) {
                    return (int) (o2.price - price);
                } else {
                    return (int) (price - o2.price);
                }
            }
        };

        public static Comparator<Event> REVIEW = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                int reviews = (int) ((Event) o1).getReviewsnumber();

                if(order.equalsIgnoreCase("up")) {
                    return (int) (o2.reviewsNumber - reviews);
                } else {
                    return (int) (reviews - o2.reviewsNumber);
                }
            }
        };
    }
}
