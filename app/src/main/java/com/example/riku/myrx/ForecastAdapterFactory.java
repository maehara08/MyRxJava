package com.example.riku.myrx;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by riku_maehara on 16/02/15.
 */
public class ForecastAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate          = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> adapter = gson.getAdapter(JsonElement.class);
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out,value);
            }

            @Override
            public T read(JsonReader in) throws IOException {

                JsonElement element = getTargetElement(adapter.read(in));

                return delegate.fromJsonTree(element);
            }
        };

    }
    private JsonElement getTargetElement(JsonElement element) {

        if (!element.isJsonObject()) return element;

        JsonObject obj = element.getAsJsonObject();

        /* targetにたどり着くまで必要なnodeを教えてあげる */
        if (obj.has("forecasts")) {
            return obj.get("forecasts");


        }
        return getTargetElement(element);

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
