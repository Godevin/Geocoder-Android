package com.example.utilisateur.geoloc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class CityActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView Department;
    TextView CP;
    TextView Population;
    TextView Superficie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        String ville = (getIntent().getStringExtra("city"));

            setTitle(ville);
        if (savedInstanceState==null){
            new MyTask(this).execute(ville);
        }


           Department = (TextView) findViewById(R.id.tvDepartement);
            CP = ((TextView) findViewById(R.id.tvCP));
            Population = ((TextView) findViewById(R.id.tvPopulation));
            Superficie = ((TextView) findViewById(R.id.tvSuperficie));


    }
    public void updateCityInfo(String s) {
        try {
            JSONObject js = new JSONObject(s);
            JSONArray values = js.getJSONArray("records");
            JSONArray fields = new JSONArray();

            for (int i = 0; i < values.length(); i++) {

                JSONObject ville = values.getJSONObject(i);

                ville = new JSONObject(ville.get("fields").toString());

                fields.put(ville);
            }

            JSONObject current = (JSONObject)fields.get(0);

            Department.setText(current.getString("nom_dept"));
            CP.setText(current.getString("postal_code"));
            Population.setText(current.getString("population"));
            Superficie.setText(current.getString("superficie"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("DEPARTEMENT", Department.getText().toString());
        outState.putString("CP", CP.getText().toString());
        outState.putString("POPULATION", Population.getText().toString());
        outState.putString("SUPERFICIE", Superficie.getText().toString());
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Department.setText(savedInstanceState.getString("DEPARTEMENT"));
        CP.setText(savedInstanceState.getString("CP"));
        Population.setText(savedInstanceState.getString("POPULATION"));
        Superficie.setText(savedInstanceState.getString("SUPERFICIE"));
    }
}
