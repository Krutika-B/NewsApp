package com.example.newsapplication.di;

import android.app.Application;

import com.example.newsapplication.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
@Singleton // Scoped application wise : AppComponent owns the singleton scope
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
