package com.example.android.timetable;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.timetable.data.TimetableContract.TimetableEntry;
import java.util.ArrayList;

/**
 * Created by Sudhanshu on 23-08-2017.
 */

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<CardItem> mCardItems;

    public MyCardAdapter(Context context, ArrayList<CardItem> items) {
        mContext = context;
        mCardItems = items;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, totalSubjectsTextView, overallPerTextView;

        public MyViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title_card_view);
            totalSubjectsTextView = (TextView) view.findViewById(R.id.card_total_subjects);

            //Setting up Click Listener
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, titleTextView.getText() + " clicked!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,ActivityAddToDay.class);
                    Uri intentUri = ContentUris.withAppendedId(TimetableEntry.CONTENT_URI,
                            getDayId(titleTextView.getText().toString()));
                    intent.setData(intentUri);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    // Map the day to the id
    public static int getDayId(String day) {
        int id;
        switch(day){
            case "Monday": id = TimetableEntry.DAY_MONDAY;
                break;
            case "Tuesday": id = TimetableEntry.DAY_TUESDAY;
                break;
            case "Wednesday": id = TimetableEntry.DAY_WEDNESDAY;
                break;
            case "Thursday": id = TimetableEntry.DAY_THURSDAY;
                break;
            case "Friday": id=TimetableEntry.DAY_FRIDAY;
                break;
            case "Saturday": id = TimetableEntry.DAY_SATURDAY;
                break;
            case "Sunday": id = TimetableEntry.DAY_SUNDAY;
                break;
            default: id = TimetableEntry.DAY_NO_DAY;
        }
        return id;
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
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }
}
