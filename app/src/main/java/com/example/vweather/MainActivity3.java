package com.example.vweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity3 extends AppCompatActivity {
    private static SharedPreferences preferences;
    public static Activity fa;
    EditText name;
    EditText city_name;
    public static final String SHARED_PREF = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        name=findViewById(R.id.editTextTextPersonName);
        city_name=findViewById(R.id.editTextCityName);
        fa = this;

    }

    public void submitted(View view)
    {
        String u_name=name.getText().toString();
        String c_name=city_name.getText().toString();
//        Log.d("username",u_name);

//        i.putExtra("name", u_name);
//        i.putExtra("city_name",c_name);
        if(!u_name.matches("")) {
            setDefaults("UserName",u_name,this);
        }
        if(!c_name.equals(""))
        {
            setDefaults("CityName",c_name,this);
        }

        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
        finish();
//        Log.d("usernam is",preferences.getString("UserName"," "));
    }
    public static void setDefaults(String key, String value, Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        // or editor.commit() in case you want to write data instantly
    }
}