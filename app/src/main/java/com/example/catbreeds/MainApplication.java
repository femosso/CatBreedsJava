package com.example.catbreeds;

import android.app.Application;

import com.example.catbreeds.di.component.DaggerApplicationComponent;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainApplication extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return activityDispatchingAndroidInjector;
    }
}