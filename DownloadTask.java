package com.example.shagniho.weatherapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shagniho on 1/26/2017.
 */

public class DownloadTask extends AsyncTask<String , Void , String> {


    @Override
    protected String doInBackground(String... urls ) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data != -1)
            {
                char current = (char)data;
                result += current;
                data = reader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            Double temperature = Double.parseDouble(weatherData.getString("temp"));

            double temp = (double) (temperature -273.15);

            String placeName = jsonObject.getString("name");

            String t = String.valueOf(temp) + "Degree Celcius";
            MainActivity.temperatureTextView.setText(String.valueOf(temp));
            MainActivity.placeTextView.setText(placeName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
