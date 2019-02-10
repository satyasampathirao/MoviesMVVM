package mydu.moviesmvvm.data.intercepters;

public class NoConnectivityEvent {

    public static NoConnectivityEvent instance() {
        return new NoConnectivityEvent();
    }

    private NoConnectivityEvent() {

    }
}
