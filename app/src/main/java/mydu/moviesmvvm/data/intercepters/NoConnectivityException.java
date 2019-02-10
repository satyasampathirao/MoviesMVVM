package mydu.moviesmvvm.data.intercepters;

import android.content.Context;


import java.io.IOException;

import mydu.moviesmvvm.R;

public class NoConnectivityException extends IOException {
    private Context context;

    public NoConnectivityException(Context context) {
        this.context = context;
    }

    @Override
    public String getMessage() {
        return context.getString(R.string.error_no_internet_connection);
    }
}
