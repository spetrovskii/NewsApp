package com.example.newsappold;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final int NEWS_LOADER_ID = 1;
    public static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?";

    ListView mListView;
    TextView mDefaultTextView;
    NewsAdapter mAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        mListView = (ListView) findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mDefaultTextView = (TextView) findViewById(R.id.default_text_view);

        //Check if there is an internet connection
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mDefaultTextView.setVisibility(View.VISIBLE);
            mDefaultTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "21f92578-7925-4d4f-be9b-e987d447af76");
        uriBuilder.build();
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        if(news.size() == 0){
            mProgressBar.setVisibility(View.GONE);
            mDefaultTextView.setVisibility(View.VISIBLE);
            mDefaultTextView.setText(R.string.no_data);
        }else{

            ListView list = findViewById(R.id.list_view);
            mAdapter = new NewsAdapter(this, news);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    News currentItem = mAdapter.getItem(position);
                    assert currentItem != null;
                    String link = currentItem.getUrl();

                    Uri webPage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            };
            list.setOnItemClickListener(itemListener);

            mProgressBar.setVisibility(View.GONE);
            mListView.setAdapter(mAdapter);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

}