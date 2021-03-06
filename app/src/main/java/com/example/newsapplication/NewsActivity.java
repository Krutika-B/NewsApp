package com.example.newsapplication;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;


import com.example.newsapplication.adapter.NewsListAdapter;
import com.example.newsapplication.response.Article;
import com.example.newsapplication.response.News;
import com.example.newsapplication.utils.Constants;
import com.example.newsapplication.utils.NetworkUtil;
import com.example.newsapplication.viewmodel.NewsResource;
import com.example.newsapplication.viewmodel.NewsViewModel;
import com.example.newsapplication.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.android.support.DaggerAppCompatActivity;


public class NewsActivity extends DaggerAppCompatActivity implements NewsListAdapter.OnNewsClickListener {

    @Inject
    ViewModelProviderFactory providerFactory;
    private NewsViewModel viewModel;
    private ProgressBar progressBar;

    @Inject
    NetworkUtil networkUtil;

    private RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    private NewsListAdapter adapter;
    private TextView newsError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        newsError = findViewById(R.id.news_error);
        initViewModel();
        getNewsList();
        setupRecyclerView();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, providerFactory).get(NewsViewModel.class);
        viewModel.init();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void getNewsList() {
        showProgressBar(true);
        viewModel.getNewsList()
                .observe(this, new Observer<NewsResource<News>>() {
                    @Override
                    public void onChanged(NewsResource<News> newsNewsResource) {
                        switch (newsNewsResource.status) {
                            case SUCCESS:
                                showProgressBar(false);
                                if (newsNewsResource.data != null && newsNewsResource.data.getStatus().equals(Constants.STATUS_OK)) {
                                    newsError.setVisibility(View.GONE);
                                    if (!articles.isEmpty()) {
                                        articles.clear();
                                    }
                                    articles = newsNewsResource.data.getArticle();
                                    if (articles.size() != 0) {
                                        adapter = new NewsListAdapter(articles, NewsActivity.this, NewsActivity.this);
                                        recyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else newsError.setVisibility(View.VISIBLE);
                                } else {
                                    newsError.setVisibility(View.VISIBLE);
                                    Toast.makeText(NewsActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case ERROR:
                                showProgressBar(false);
                                newsError.setVisibility(View.VISIBLE);
                                Toast.makeText(NewsActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
    }

    private void showProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNewsClick(int position) {
        Intent newsDetailsIntent = NewsDetailsActivity.newIntent(this, articles.get(position).getSource().getName(), articles.get(position).getDescription(),articles.get(position).getPublishedAt(),articles.get(position).getTitle());
        startActivity(newsDetailsIntent);
    }

}