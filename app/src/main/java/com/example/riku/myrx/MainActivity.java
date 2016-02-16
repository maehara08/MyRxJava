package com.example.riku.myrx;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cookpad.android.rxt4a.schedulers.AndroidSchedulers;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // JSONのパーサー
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(new ForecastAdapterFactory())
                .create();


        // RestAdapterの生成
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://weather.livedoor.com")
//                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build();



        // 非同期処理の実行
        adapter.create(WeatherAPI.class).get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(WeatherEntity weather) {
                        Log.d("MainActivity", "onNext()");
                        if (weather != null) {

                            ((TextView) findViewById(R.id.text)).setText(weather.weather.get(0).description);
                        }
                    }
                });
    }
    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            // {forecasts[] -> 0 -> {dataLabel, telop, tem}}
            JSONArray forecastsArray = jsonObject.getJSONArray("forecasts");
            // 0番目のものが今日の天気なので取得する
            JSONObject todayWeatherJson = forecastsArray.getJSONObject(0);
            // 今日
            String date = todayWeatherJson.getString("date");
            Log.d("Date", date);
            String telop = todayWeatherJson.getString("telop");
            Log.d("Telop", telop);
            String dataLabel = todayWeatherJson.getString("dateLabel");
            Log.d("DataLabel", dataLabel);

//            String date = todayWeatherJson.getString("date");
//            String telop = todayWeatherJson.getString("telop");
//            String dataLabel = todayWeatherJson.getString("dateLabel");



            JSONObject temperatureJson = todayWeatherJson.getJSONObject("temperature");
            JSONObject minJson = temperatureJson.get("min") != null ? temperatureJson.getJSONObject("min") : null;
            String min = "";


            JSONObject imageJson = todayWeatherJson.getJSONObject("image");
            String imageUrl = imageJson.getString("url");


            if (minJson != null) {
                min = minJson.getString("celsius");
            }
            JSONObject maxJson = temperatureJson.get("max") != null ? temperatureJson.getJSONObject("max") : null;
            String max = "";
            if (maxJson != null) {
                max = maxJson.getString("celsius");
            }
            Log.d("Min ~ Max", min + "〜" + max);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }















}
