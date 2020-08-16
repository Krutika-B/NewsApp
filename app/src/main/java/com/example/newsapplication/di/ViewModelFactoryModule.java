package com.example.newsapplication.di;

import com.example.newsapplication.viewmodel.ViewModelProviderFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    // Responsible for doing dependency for ViewModelFactory class

    @Binds // Provides instance of ViewModelProviderFactory
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
