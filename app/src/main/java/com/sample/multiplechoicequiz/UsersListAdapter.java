package com.sample.multiplechoicequiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

class UsersListAdapter extends ArrayAdapter<Users> {

    private Context mContext;
    private int mResource;


    public UsersListAdapter(@NonNull Context context, int resource, @NonNull List<Users> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the persons information
        String uName = getItem(position).getuName();
        int score = getItem(position).gethScore();

        //Create the person object with the information
        Users users = new Users(uName, score);


            LayoutInflater inflater = LayoutInflater.from(mContext);//setting the inflater
            convertView = inflater.inflate(mResource, parent, false);

            TextView tUserName = (TextView) convertView.findViewById(R.id.userName);
            TextView tScore = (TextView) convertView.findViewById(R.id.score);

            tUserName.setText(uName);
            tScore.setText(String.valueOf(score));


        return convertView;
    }
}
