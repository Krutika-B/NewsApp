package com.example.newsapplication.di;

import com.example.newsapplication.viewmodel.NewsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NewsViewModelModule {
    // Responsible for dependency for AuthViewModelsModule itself

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    public abstract ViewModel bindAuthViewModel(NewsViewModel viewModel);
}
