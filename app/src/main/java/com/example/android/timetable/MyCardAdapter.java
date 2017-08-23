package com.example.android.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Sudhanshu on 23-08-2017.
 */

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<CardItem> mCardItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, totalSubjectsTextView, overallPerTextView;

        public MyViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title_card_view);
            totalSubjectsTextView = (TextView) view.findViewById(R.id.card_total_subjects);
            overallPerTextView = (TextView) view.findViewById(R.id.card_overall_per);

            //Setting up Click Listener
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, titleTextView.getText() + " clicked!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public MyCardAdapter(Context context, ArrayList<CardItem> items) {
        mContext = context;
        mCardItems = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CardItem cardItem = mCardItems.get(position);
        holder.titleTextView.setText(cardItem.getTitle());
        holder.totalSubjectsTextView.setText(Integer.toString(cardItem.getTotalSubjects()));
        holder.overallPerTextView.setText(Double.toString(cardItem.getOverallPercentage()));
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }
}
