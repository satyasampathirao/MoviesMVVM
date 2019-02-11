package mydu.moviesmvvm.data.remote;


import com.google.gson.JsonElement;

import io.reactivex.Observable;
import mydu.moviesmvvm.data.model.TopHeadLinesModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("top-headlines?country=in&apiKey=b1422282ee3f4716ba138edcf5523d68")
    Observable<JsonElement> getTopHeadLines();

    @GET("top-headlines?country=in&apiKey=b1422282ee3f4716ba138edcf5523d68")
    Call<TopHeadLinesModel> getTopRatedHeadLinesNews();


}
