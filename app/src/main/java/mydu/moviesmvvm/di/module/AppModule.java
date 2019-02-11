package mydu.moviesmvvm.di.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mydu.moviesmvvm.BuildConfig;
import mydu.moviesmvvm.data.intercepters.ConnectivityInterceptor;
import mydu.moviesmvvm.data.intercepters.UnauthorizedInterceptor;
import mydu.moviesmvvm.data.remote.ApiConstants;
import mydu.moviesmvvm.data.remote.ApiEndPoint;
import mydu.moviesmvvm.data.remote.WebService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndPoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();


    }

    @Provides
    @Singleton
    WebService providesApiService(Retrofit retrofit) {
        return retrofit.create(WebService.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }



    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.MINUTES);
        builder.hostnameVerifier((hostname, session) -> true);

        if (BuildConfig.DEBUG) {
            // Added interceptor for http logging
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.addInterceptor(new UnauthorizedInterceptor());
        builder.addInterceptor(new ConnectivityInterceptor(context));

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json");

           /* String userToken = appPref.getAuthToken();
            if (!TextUtils.isEmpty(userToken)) {
                requestBuilder.addHeader("Authorization", "Bearer " + userToken);
            }*/

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return builder.build();
    }
    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

}
