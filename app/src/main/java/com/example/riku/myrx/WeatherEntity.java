package com.example.riku.myrx;

import java.util.List;

/**
 * Created by riku_maehara on 16/02/15.
 */
public class WeatherEntity {
    public String base;
    public List<Weather> weather;
    public class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }

}
