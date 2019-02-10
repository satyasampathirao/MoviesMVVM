package mydu.moviesmvvm.ui.home;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import mydu.moviesmvvm.data.remote.WebService;
import mydu.moviesmvvm.di.ViewModelProviderFactory;
import mydu.moviesmvvm.rx.SchedulersFacade;

@Module
public class MainActivityModule {
    @Provides
    ViewModelProvider.Factory mainActivityViewModelProvider(MainActivityViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    MainActivityViewModel provideAddAccountViewModel(WebService apiService,SchedulersFacade schedulersFacade) {
        return new MainActivityViewModel(apiService,schedulersFacade);
    }


}
