package com.cityprograms.eventhunter;

import android.app.Application;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class MyApplication extends Application {
    public void onCreate() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "5LnsiRbvi42DhuyywNLrUFuUyxAsUqT9RlCPrClu", "F1HxtLht8X3WHrgijj6zSgKaOjENV37n0echKWph");

        ParsePush.subscribeInBackground("", new SaveCallback() {

            public void done(ParseException e) {
                if (e != null) {
                    Log.d("EventHunter", "SUCCESS");
                } else {
                    Log.d("EventHunter", "NO SUCCESS");
                }
            }
        });
    }
}