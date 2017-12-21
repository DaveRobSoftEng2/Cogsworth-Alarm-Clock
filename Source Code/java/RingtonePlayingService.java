package com.davey.davescogsworth;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

import static com.davey.davescogsworth.QueryWeather.fetchWeatherData;

/**
 * Created by Davey on 11/10/2017.
 *
 * edited by rob on 12/02/2017
 */

public class RingtonePlayingService extends Service {

    /**
    * Robs variables
    */
    private String OPEN_WEATHER_API_KEY = "d5a48d4e0073d019070c9731aecd6914";
    private double mLongitude, mLatitude;
    private FusedLocationProviderClient mFusedLocationClient;
    private float ttsSpeed = (float)0.85;

    TextToSpeech tts;

    MediaPlayer media_song;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        tts = new TextToSpeech(this.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(ttsSpeed);
                }
            }
        });

        // checks permission before we get the location service
        // app would be unhappy if we do not do this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //use intent data to check what song, noise, was chosen
        String time = intent.getStringExtra("time");
        String label = intent.getStringExtra("label");
        String option = intent.getStringExtra("option");
        //String repeat = intent.getStringArrayListExtra("repeat");
        //String repeat = intent.getStringArrayListExtra("repeat");
        //ArrayList<Boolean> repeat = intent.getBooleanArrayExtra("repeat");
        Boolean repeat = intent.getBooleanExtra("repeat", false);
        Boolean vibrate = intent.getBooleanExtra("vibrate", false);

        media_song = MediaPlayer.create(this, R.raw.chewy);
        media_song.start();

        // calls a series of functions that results in the tts playing of the weather report for the day
        readWeatherReport();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this,
                "on Destroy Called", Toast.LENGTH_SHORT).show();
    }

    /**
     * This function checks to make sure we have permission to access the phones location because getting
     * its latitude and longitude and then passing them to WeatherReport
     */
    public void readWeatherReport(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mLongitude = location.getLongitude();
                    mLatitude = location.getLatitude();
                    new WeatherReport().execute(createApiRequestUrl(String.valueOf(mLongitude), String.valueOf(mLatitude)));
                }
            }
        });
    }

    /**
     *  This handles getting the weather using AsyncTask
     *  calls OpenWeatherMapAPI and reads the weather using Text to Speech
     */
    public class WeatherReport extends AsyncTask<String, Void, Weather> {
        private Exception exception;

        @Override
        protected Weather doInBackground(String... url){
            try{
                Weather weather;
                weather = fetchWeatherData(url[0]);
                return weather;

            } catch (Exception e) {
                this.exception = e;
                Log.i("WEATHER_REPORT", "doInBackground: failed to call fetchWeatherData(), " + e.toString());
                return new Weather();
            }
        }

        @Override
        protected void onPostExecute(Weather result){

            String report = result.reportWeather();

            tts.speak(report, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     *
     * @param lon takes a string for the longitude where the phone is located
     * @param lat takes a string for the latitude where the phone is located
     * @return the properly constructed url for the OpenWeatherAPI call
     */
    public String createApiRequestUrl(String lon, String lat){
        String url = "";

        url += "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&APPID=" + OPEN_WEATHER_API_KEY + "&units=imperial";

        return url;
    }

    /**
     * checks the permission of the app to access phone location
     */
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){

            Log.e("RingtonePlayingService", "checkPermission: permission was not given to access phone location");
        }
    }
}
