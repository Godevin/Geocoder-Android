package com.example.utilisateur.geoloc;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MyTask extends AsyncTask<String, Void, String> {
    CityActivity activity;

    public MyTask(CityActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = "http://public.opendatasoft.com/api/records/1.0/search?dataset=correspondance-code-insee-code-postal&q=" + strings[0];
        try{
            URL url = new URL(s);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            return(readStream(in));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line + "\n");
        br.close();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        activity.updateCityInfo(s);
    }
}
