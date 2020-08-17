package com.example.newsapplication.di;

import com.example.newsapplication.NewsActivity;
import com.example.newsapplication.NewsDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(
            modules = {NewsViewModelModule.class}
    )
    abstract NewsActivity contributeNewsActivity();

    @ContributesAndroidInjector
    abstract NewsDetailsActivity contributeNewsDetailsActivity();
}
