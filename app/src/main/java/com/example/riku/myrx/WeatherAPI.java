package com.example.riku.myrx;

import com.squareup.okhttp.Call;

import org.json.JSONObject;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by riku_maehara on 16/02/15.
 */
public interface WeatherAPI {
//    @GET("/forecast/webservice/json/v1?city=400040")
//    public JSONObject get();

    @GET("/forecast/webservice/json/v1")
    public Observable<Forecast> getWeather(@Query("city") final String city);

}
