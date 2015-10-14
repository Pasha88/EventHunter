package com.cityprograms.eventhunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

    private ListView mListView;

    private List<Comment> myCommentsList;

    public CommentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_comments, container, false);
        mListView = (ListView) fragmentView.findViewById(R.id.MyListView);

        String eventComments = EventActivity.eventComment;

        dataQuery(eventComments);

        return fragmentView;
    }

    public void dataQuery(String eventsComments) {
        myCommentsList = new ArrayList<Comment>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("eventId", eventsComments);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventsParseList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + eventsParseList.size() + " scores");

                    for (ParseObject parseObject : eventsParseList) {
                        String objectId = parseObject.getObjectId();
                        String eventId = (String) parseObject.get("eventId");
                        String userNme = (String) parseObject.get("userName");
                        String comment = (String) parseObject.get("comment");

                        int mark = (int) parseObject.get("mark");

                        Comment commentObject = new Comment(objectId, eventId, userNme, comment, mark);

                        myCommentsList.add(commentObject);
                    }

                    mListView.setAdapter(new MyAdapter());

                } else {
                    Log.d("score", "Error: " + e.getMessage());
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
            return myCommentsList.size();
        }

        @Override
        public Object getItem(int position) {
            return myCommentsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = getActivity().getLayoutInflater().inflate(R.layout.row_comment, null);

            TextView textViewRow = (TextView) rowView.findViewById(R.id.textUserName);
            textViewRow.setText(myCommentsList.get(position).getUserName());

            TextView textViewRow2 = (TextView) rowView.findViewById(R.id.textUserComment);
            textViewRow2.setText(myCommentsList.get(position).getComment());

            TextView textViewRow3 = (TextView) rowView.findViewById(R.id.textUserMark);
            textViewRow3.setText(String.valueOf(myCommentsList.get(position).getMark()));

            return rowView;
            //that
        }
    }
}
