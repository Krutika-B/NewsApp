package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsapplication.adapter.NewsListAdapter;
import com.example.newsapplication.response.Article;
import com.example.newsapplication.utils.NetworkUtil;
import com.example.newsapplication.viewmodel.NewsViewModel;
import com.example.newsapplication.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class NewsDetailsActivity extends DaggerAppCompatActivity {
    private static final String EXTRA_SELECTED_NEWS_PUBLISHER = "EXTRA_SELECTED_NEWS_PUBLISHER";
    private static final String EXTRA_NEWS_TITLE= "EXTRA_NEWS_TITLE";
    private static final String EXTRA_NEWS_DESCRIPTION = "EXTRA_NEWS_DESCRIPTION";
    private static final String EXTRA_NEWS_PUBLISHER_DATE = "EXTRA_NEWS_PUBLISHER_DATE";

    private String selectedNewsSourceTitle;
    private String selectedNewsTitle;
    private String selectedNewsPublishedDate;
    private String selectedNewsDescription;
    @Inject
    ViewModelProviderFactory providerFactory;
    private NewsViewModel viewModel;
    private ProgressBar progressBar;

    @Inject
    NetworkUtil networkUtil;

    TextView title,  published_ad, source ,desc ,butttonback;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getExtrasFromIntent(getIntent());
        title = findViewById(R.id.title1);
        published_ad = findViewById(R.id.publishedAt1);
        source = findViewById(R.id.source1);
        imageView = findViewById(R.id.img);
        desc =findViewById(R.id.desc);
        butttonback = findViewById(R.id.button_back);
        title.setText(selectedNewsTitle);
        published_ad.setText(selectedNewsPublishedDate);
        source.setText(selectedNewsSourceTitle);
        desc.setText(selectedNewsDescription);
        butttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void getExtrasFromIntent(Intent intent) {
        selectedNewsSourceTitle = intent.getStringExtra(EXTRA_SELECTED_NEWS_PUBLISHER);
        selectedNewsDescription = intent.getStringExtra(EXTRA_NEWS_DESCRIPTION);
        selectedNewsPublishedDate = intent.getStringExtra(EXTRA_NEWS_PUBLISHER_DATE);
        selectedNewsTitle = intent.getStringExtra(EXTRA_NEWS_TITLE);
    }

    // Create intent for NewsDetailsActivity with data to initiate it
    public static Intent newIntent(Context context, String articleSourceTitle, String articleDescription, String articlePublishedDate,String articleTitle) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_PUBLISHER_DATE, articlePublishedDate);
        intent.putExtra(EXTRA_SELECTED_NEWS_PUBLISHER, articleSourceTitle);
        intent.putExtra(EXTRA_NEWS_TITLE, articleTitle);
        intent.putExtra(EXTRA_NEWS_DESCRIPTION, articleDescription);

        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
