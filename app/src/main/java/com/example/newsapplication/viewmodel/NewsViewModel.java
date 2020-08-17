package com.example.newsapplication.viewmodel;

import com.example.newsapplication.repository.NewsRepository;
import com.example.newsapplication.response.Article;
import com.example.newsapplication.response.News;
import com.example.newsapplication.utils.Constants;
import com.example.newsapplication.utils.NetworkUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private static final String TAG = "NewsViewModelModule";

    @Inject
    NetworkUtil networkUtil;
    private NewsRepository repository;
    private MutableLiveData<List<Article>> articleList = new MutableLiveData<>();
    private MutableLiveData<Boolean> saveNewsSuccess = new MutableLiveData<>();
    private LiveData<NewsResource<News>> newsResourceLiveData;

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    public void init() {
        if (newsResourceLiveData != null) {
            return;
        }
        fetchNewsFromApi();
    }

    private void fetchNewsFromApi() {
        repository.getNewsApi();
        newsResourceLiveData = Transformations.map(repository.getNews(), news -> {
            if (news.getStatus().equals(Constants.STATUS_FAILED)) {
                return NewsResource.error("Could not fetch", null);
            }
            articleList.postValue(news.getArticle());
            return NewsResource.success(news);
        });
    }

    public LiveData<NewsResource<News>> getNewsList() {
        return newsResourceLiveData;
    }


}
