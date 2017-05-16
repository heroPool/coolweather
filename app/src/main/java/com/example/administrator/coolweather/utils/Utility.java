package com.example.administrator.coolweather.utils;

import android.text.TextUtils;

import com.example.administrator.coolweather.db.City;
import com.example.administrator.coolweather.db.County;
import com.example.administrator.coolweather.db.Province;
import com.example.administrator.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/22.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     *
     * @param response 请求的字符串
     * @return
     */
    public static boolean handleProvinceResonse(String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONArray allProvinces = null;
            try {
                allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProviceCode(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级信息
     *
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId) {

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);

                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     *
     * @param response
     * @param cityId
     * @return
     */

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            JSONArray allConuties = null;
            try {
                allConuties = new JSONArray(response);
                for (int i = 0; i < allConuties.length(); i++) {
                    JSONObject countiesObject = allConuties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countiesObject.getString("name"));
                    county.setWeatherId(countiesObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static Weather handleWeatherResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
