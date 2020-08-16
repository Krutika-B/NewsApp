package com.example.newsapplication.di;

import dagger.Module;

import javax.inject.Singleton;
import android.app.Application;


import com.example.newsapplication.repository.cache.NewsDatabaseHelper;
import com.example.newsapplication.utils.Constants;
import com.example.newsapplication.utils.NetworkUtil;
import com.google.gson.Gson;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module

public class AppModule {
    // App level module which will have Application level dependencies like Repository, Glide Instance

    @Singleton
    @Provides
    static NewsDatabaseHelper provideNewsDatabaseHelper(Application application, Gson gson) {
        return new NewsDatabaseHelper(application, gson);
    }

    @Singleton
    @Provides
    static NetworkUtil provideNetworkUtil(Application application) {
        return new NetworkUtil(application);
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        return retrofitBuilder.build();
    }
}
