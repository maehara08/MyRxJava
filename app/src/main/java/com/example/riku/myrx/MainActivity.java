package com.example.riku.myrx;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    TextView weatherView;
    TextView dateView;
    TextView locationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView=(TextView)findViewById(R.id.dateView);
        locationView=(TextView)findViewById(R.id.locationView);
        weatherView=(TextView)findViewById(R.id.weatherView);





    }
    public void tokyo(View v){
        setAPI("130010");

    }
    public void osaka(View v){
        setAPI("270000");

    }
    public void hokkaido(View v){
        setAPI("016010");

    }
    public void setAPI(String api) {

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
        final WeatherAPI api2 = adapter.create(WeatherAPI.class);

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

                String location=weather.getLocation().getCity();
                String date=weather.getForecasts().get(0).getDate();
                String weatherString=weather.getForecasts().get(0).getTelop();
                locationView.setText(location);
                dateView.setText(date);
                weatherView.setText(weatherString);



            }
        };

        api2.getWeather(api)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);






    }
















}
















