package com.example.riku.myrx;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by riku_maehara on 16/02/15.
 */
public interface WeatherAPI {
    @GET("/forecast/webservice/json/v1?city=400040")
    public Observable<WeatherEntity> get();


}
