package com.example.riku.myrx;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by riku_maehara on 16/02/16.
 */
public class JsonForecast {

        @SerializedName("id")
        int mId;

        @SerializedName("name")
        String mName;

        public JsonForecast(int id, String name ) {
            this.mId = id;
            this.mName = name;
        }

}
