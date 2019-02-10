package mydu.moviesmvvm.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mydu.moviesmvvm.ui.home.MainActivity;
import mydu.moviesmvvm.ui.home.MainActivityModule;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();
}
