package mydu.moviesmvvm.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;

import io.reactivex.disposables.CompositeDisposable;
import mydu.moviesmvvm.data.Response;
import mydu.moviesmvvm.data.model.TopHeadLinesModel;
import mydu.moviesmvvm.data.remote.WebService;
import mydu.moviesmvvm.rx.SchedulersFacade;
import mydu.moviesmvvm.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivityViewModel extends BaseViewModel<MainActivityNavigator> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<Response> topRatedResponse = new MutableLiveData<>();

    MainActivityViewModel(WebService webService, SchedulersFacade schedulersFacade) {
        super(webService, schedulersFacade);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public MutableLiveData<Response> getTopRatedResponse() {
        return topRatedResponse;
    }

    public void getTopHeadLines() {
        disposables.add(getWebClient().getTopHeadLines()
                .subscribeOn(getSchedulersFacade().io())
                .observeOn(getSchedulersFacade().ui())
                .doOnSubscribe(__ -> topRatedResponse.setValue(Response.loading()))
                .subscribe(
                        greeting -> topRatedResponse.setValue(Response.success(greeting.toString())),
                        throwable -> topRatedResponse.setValue(Response.error(throwable.getMessage()))
                )
        );


    }

    public void getMenuItems() {
        // fetching from db
        // getNavigator().onMenuItemsReceived(getAppDatabase().getMenuItems());

        setIsLoading(true);
        Call<TopHeadLinesModel> call = getWebClient().getTopRatedHeadLinesNews();
        call.enqueue(new Callback<TopHeadLinesModel>() {
            @Override
            public void onResponse(Call<TopHeadLinesModel> call, retrofit2.Response<TopHeadLinesModel> response) {
                setIsLoading(false);
                if (!response.isSuccessful()) {
                    return;
                }

                new Handler().postDelayed(() -> {
                    getNavigator().onTopHeadLinesNews(response.body());
                }, 2000);
            }

            @Override
            public void onFailure(Call<TopHeadLinesModel> call, Throwable t) {
                setIsLoading(false);
                getNavigator().onApiError(t);
            }
        });
    }


}
