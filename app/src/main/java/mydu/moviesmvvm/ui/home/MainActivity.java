package mydu.moviesmvvm.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mydu.moviesmvvm.R;
import mydu.moviesmvvm.data.Response;
import mydu.moviesmvvm.data.model.TopHeadLinesModel;
import mydu.moviesmvvm.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityNavigator {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel.class);
        mViewModel.setNavigator(this);
       /* mViewModel.getTopHeadLines();
        mViewModel.getTopRatedResponse().observe(this, this::processTopRatedLinesResponse);*/
       mViewModel.getMenuItems();

    }

    private void processTopRatedLinesResponse(Response response) {
        if (response == null) Response.error(getString(R.string.something_unexpected));
        switch (response.status) {
            case LOADING:
                showProgress();
                break;

            case SUCCESS:
                hideProgress();
                break;

            case ERROR:
                hideProgress();
                break;
        }
    }

    @Override
    public void onApiError(Throwable throwable) {

    }

    @Override
    public void onTopHeadLinesNews(TopHeadLinesModel topHeadLinesModel) {
        Toast.makeText(this, "" + topHeadLinesModel, Toast.LENGTH_SHORT).show();

    }
}
