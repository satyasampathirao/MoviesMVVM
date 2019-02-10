package mydu.moviesmvvm.ui.home;

import java.util.ArrayList;

import mydu.moviesmvvm.data.model.TopHeadLinesModel;
import mydu.moviesmvvm.ui.base.BaseNavigator;

public interface MainActivityNavigator extends BaseNavigator {
    void onTopHeadLinesNews(TopHeadLinesModel topHeadLinesModel);

}
