package com.davey.davescogsworth;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by rob on 11/15/2017.
 */

public class QueryWeather {

    private static final String LOG_TAG = QueryWeather.class.getSimpleName();

    private QueryWeather(){}

    /**
     *
     * @param requestUrl uses the string to make a request from API
     * @return a weather object
     */
    public static Weather fetchWeatherData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        Weather weather = extractFeatureFromJson(jsonResponse);

        Log.i("fetchWeatherData", "fetchWeatherData: finished things, got: " + weather.getLocation());
        Log.i("fetchWeatherData", "fetchWeatherData: " + weather.getHighTemp());
        Log.i("fetchWeatherData", "fetchWeatherData: " + weather.getLowTemp());
        Log.i("fetchWeatherData", "fetchWeatherData: " + weather.getCurrentTemp());
        Log.i("fetchWeatherData", "fetchWeatherData: " + weather.getDescription());

        return weather;
    }

    /**
     *
     * @param stringUrl turns this into a proper url or throws an execption
     * @return a proper url
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem biulding URL ", e);
        }
        return url;
    }

    /**
     *
     * @param url the URL used to make the request
     * @return the JSON data or an empty string if url was null
     * @throws IOException if json could not be retrieved
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            Log.i("makeHttpRequest", url.toString());
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving weather JSON data.", e);
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     *
     * @param inputStream the stream containing the JSON data
     * @return JSON response in string form
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


    private static Weather extractFeatureFromJson(String WeatherJSON){
        if(TextUtils.isEmpty(WeatherJSON)){
            return null;
        }

        Weather weather = new Weather();

        try {
            JSONObject jsonResponse;
            jsonResponse = new JSONObject(WeatherJSON);

            JSONArray weatherTagArray = jsonResponse.getJSONArray("weather");
            JSONObject mainTag = jsonResponse.getJSONObject("main");

            String location = jsonResponse.getString("name");
            weather.setLocation(location);

            String description = weatherTagArray.getJSONObject(0).getString("description");
            weather.setDescription(description);

            String currentTemp = mainTag.getString("temp");
            weather.setCurrentTemp(currentTemp);

            String lowTemp = mainTag.getString("temp_min");
            weather.setLowTemp(lowTemp);

            String highTemp = mainTag.getString("temp_max");
            weather.setHighTemp(highTemp);
        } catch (JSONException e) {
            Log.e("QueryWeather", "Problem parsing weather JSON results", e);
        }

        return weather;
    }

}
