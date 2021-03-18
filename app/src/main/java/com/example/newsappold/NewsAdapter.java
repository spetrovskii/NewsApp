package com.example.newsappold;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

        List<News> mNewsList;

        public NewsAdapter(@NonNull Context context, List<News> newsList) {
                super(context, 0, newsList);
                mNewsList = newsList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                ViewHolder viewHolder;

                if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                        viewHolder = new ViewHolder(convertView);
                        convertView.setTag(viewHolder);
                } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                }

                News currentNews = mNewsList.get(position);

                //News Title
                viewHolder.titleTextView.setText(currentNews.getTitle());

                //Check if there is an author of the news
                if(!currentNews.getAuthor().equals("notKnown")) {
                        viewHolder.authorTextView.setText("- " + currentNews.getAuthor());
                }else{
                        viewHolder.authorTextView.setVisibility(View.GONE);
                        viewHolder.emptyTextView.setVisibility(View.GONE);
                }

                //Set and display the date
                String date = currentNews.getDate().substring(0, 10);
                viewHolder.dateTextView.setText(date);

                //News Section
                viewHolder.sectionTextView.setText(currentNews.getSection());

                return convertView;

        }

        //ViewHolder class
        private class ViewHolder {
                final TextView titleTextView;
                final TextView emptyTextView;
                final TextView authorTextView;
                final TextView sectionTextView;
                final TextView dateTextView;

                ViewHolder(View view) {
                        this.titleTextView = view.findViewById(R.id.title_text_view);
                        this.emptyTextView = view.findViewById(R.id.empty_text_view);
                        this.authorTextView = view.findViewById(R.id.author_text_view);
                        this.sectionTextView = view.findViewById(R.id.section_text_view);
                        this.dateTextView = view.findViewById(R.id.date_text_view);
                }
        }

}
