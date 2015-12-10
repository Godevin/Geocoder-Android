package com.example.utilisateur.geoloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static final String MY_PREFS_NAME = "MyPrefsFile";
    EditText editText;
    Spinner spinText;
    String city;
    double longitude;
    double latitude;
    TextView lontext;
    TextView lattext;
    DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME,0);
        city = settings.getString("city", "");
        GPSTracker tracker = new GPSTracker(this);

        longitude = tracker.getLongitude();
        latitude = tracker.getLatitude();

        lontext = (TextView) findViewById(R.id.textView);
        lattext = (TextView) findViewById(R.id.textView2);

        lontext.setText("LONG : " + String.valueOf(latitude));
        lattext.setText("LAT : " + String.valueOf(longitude));

        editText = (EditText) findViewById(R.id.editText);
        spinText = (Spinner) findViewById(R.id.spinner);
        dbHelper = new DatabaseHandler(this);

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            System.out.println(addresses.get(0).getLocality());



    }

    public void onClick(View view) {
        Intent intent = new Intent(this, CityActivity.class);
        city = editText.getText().toString();

        if (city != ""){
            editText.setText(city);
            dbHelper.addCity(city);
        }
        if(city.equals("")){
            editText.setText(((Cursor) spinText.getSelectedItem()).getString(1));
            city = editText.getText().toString();
        }
        intent.putExtra("city", city);

        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("city", city);
        editor.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        editText.setText(((Cursor) spinText.getSelectedItem()).getString(1));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new DatabaseHandler(this);
        Cursor cursor = dbHelper.getCities();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursor,
                new String[]{"name"},
                new int[]{android.R.id.text1},
                0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinText.setAdapter(adapter);
    }
}
