package com.example.catbreeds.di.component;

import com.example.catbreeds.CatBreedsApp;
import com.example.catbreeds.di.module.FragmentBindingModule;
import com.example.catbreeds.di.module.RepositoryModule;
import com.example.catbreeds.di.module.ApplicationModule;
import com.example.catbreeds.di.scope.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        FragmentBindingModule.class,
        RepositoryModule.class})
public interface ApplicationComponent extends AndroidInjector<CatBreedsApp> {

    void inject(CatBreedsApp application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(CatBreedsApp application);

        ApplicationComponent build();
    }
}