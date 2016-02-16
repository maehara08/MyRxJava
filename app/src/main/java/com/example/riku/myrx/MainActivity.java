package com.example.riku.myrx;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cookpad.android.rxt4a.schedulers.AndroidSchedulers;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String End_Point = "http://weather.livedoor.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // RestAdapterを作成する
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(End_Point)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build();

        // 天気予報情報を取得する
        //http://weather.livedoor.com/area/forecast/200010
        final WeatherAPI api = adapter.create(WeatherAPI.class);

        final Observer observer = new Observer<Forecast>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted()");
                //必要な情報を取り出して画面に表示したい!!!



            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error : " + e.toString());
            }

            @Override
            public void onNext(Forecast weather) {
                //必要な情報を取り出して画面に表示したい!!!

                Log.d(TAG, "onNext()");

                    String s=weather.getForecasts().get(0).getTelop();
                    Log.d("Location", weather.getLocation().getCity());
//                    textView.setText(s);



            }
        };

        api.getWeather("200010")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }















/*
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
*/
}
















