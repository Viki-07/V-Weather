package com.example.vweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity2<url, stringRequest> extends AppCompatActivity {

    private static Activity fa;
    TextView temperatur;
    TextToSpeech t1;
    TextView weather;
    TextView date;
    TextView humidv;
    TextView wind;
    TextView pressure;
    TextView riseval;
    TextView setval;
    ImageView Weathericon;
    TextView qoute_text;
    TextView author_text;
    TextView greet;
    TextView location_n;
    TextView sunrise;
    String iconId;
    String x;
    String y;
    String z;
    String m;
    String firstName;
    String url;
    String user_name;
    String city_name;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivity2.this, MainActivity3.class));
            finish();

        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        temperatur = findViewById(R.id.temperature);
        weather = findViewById(R.id.weather);
        date = findViewById(R.id.textViewdate);
        humidv = findViewById(R.id.humid);
        wind = findViewById(R.id.windval);
        pressure = findViewById(R.id.pressureval);
        sunrise=findViewById(R.id.rise_text);
        Weathericon = findViewById(R.id.iconImage);
        greet = findViewById(R.id.gm);
        qoute_text = findViewById(R.id.q_text);
        author_text = findViewById(R.id.author_name);
        location_n = findViewById(R.id.location_name);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getString("UserName", " ").equals("")) {
            user_name = preferences.getString("UserName", " ").substring(0, 1).toUpperCase() + preferences.getString("UserName", " ").substring(1).toLowerCase();

        }
        if (!preferences.getString("CityName", " ").equals(""))
        {
            city_name = preferences.getString("CityName"," ").substring(0, 1).toUpperCase() + preferences.getString("CityName"," ").substring(1).toLowerCase();

        }
          location_n.setText(city_name);
        url = "https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=2b211e9ed69b3b9398972ac748f29698&units=metric&lang=hi";
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        qoute();
        greetings();
        datesetter();
        caller();

    }

    public void datesetter()
    {


        String currentDate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());

        date.setText("Today, \n"+currentDate);
    }

   public void caller()
   {
       RequestQueue queue=Volley.newRequestQueue(MainActivity2.this);


       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try{
                   // Get the JSON array
                   JSONArray array = response.getJSONArray("weather");
//
//                   // Loop through the array elements
                   for(int i=0;i<array.length();i++) {
//                       // Get current json object
                       JSONObject student = array.getJSONObject(i);

//                       // Get the current student (json object) data
                       firstName = student.getString(
                               "main");
                       iconId=student.getString("icon");
                       weather.setText(firstName);
                   }



                       String path="https://openweathermap.org/img/wn/"+iconId+"@2x.png";
                       Glide.with(MainActivity2.this).load(path).into(Weathericon);

//                       // Display the formatted json data in text view
//
                   JSONObject object=response.getJSONObject("main");
                   for (int i=0;i<object.length();i++)
                   {
                       int t=object.getInt("temp");
                        int w=object.getInt("humidity");
                        int a=object.getInt("pressure");

                      x=Integer.toString(t);
                      y=Integer.toString(w);
                      m=Integer.toString(a);

                   }
                   temperatur.setText(x+"°C");
                   humidv.setText(y+"%");
                   pressure.setText(m+" hPa");

                JSONObject object1=response.getJSONObject("wind");
                for(int i=0;i<object1.length();i++)
                {
                    double t=object1.getDouble("speed");
                    z=Double.toString(t);
                }
                wind.setText(z+" km/h");

                   int rise = 0;
                   int sset;
                   JSONObject sys=response.getJSONObject("sys");
                   for(int i=0;i<sys.length();i++)
                   {
                       rise=sys.getInt("sunrise");
                       sset=sys.getInt("sunset");
                   }
                   int rise_seconds = rise;
                   String ans="";
                   int extraTime = rise_seconds % (24 * 60 * 60);
                   int hours = extraTime / 3600;
                   int minutes = (extraTime % 3600) / 60;
                   ans += String.valueOf(hours);
                   ans += ":";
                   ans += String.valueOf(minutes);
                   sunrise.setText(ans);
           }catch (JSONException e){
                   e.printStackTrace();
               }
           }
       }, error -> {
           // below line is use to display a toast message along with our error.
           Toast.makeText(MainActivity2.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
       });

       queue.add(jsonObjectRequest);
   }
   public void sun(){

       String date = DateFormat.getDateTimeInstance().toString();
       riseval.setText(date);
   }
   public void greetings()
   {
       Calendar c = Calendar.getInstance();
       int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

       if(timeOfDay >= 0 && timeOfDay < 12){
           greet.setText("Good Morning "+user_name+" !");
       }else if(timeOfDay >= 12 && timeOfDay < 16){
           greet.setText("Good Afternoon "+user_name+" !");
       }else if(timeOfDay >= 16 && timeOfDay < 21){
           greet.setText("Good Evening "+user_name+" !");
       }else if(timeOfDay >= 21 && timeOfDay < 24){
           greet.setText("Good Night "+user_name+" !");
       }
   }
   public void qoute() {
       String q_url="https://api.quotable.io/random?maxLength=50";
       RequestQueue q_queue=Volley.newRequestQueue(MainActivity2.this);
       StringRequest myRequest = new StringRequest(Request.Method.GET, q_url,
               response -> {
                   try{
                       //Create a JSON object containing information from the API.
                       JSONObject myJsonObject = new JSONObject(response);
                       Log.d("qoute is ",myJsonObject.getString("content"));
                       qoute_text.setText("'"+myJsonObject.getString("content")+"'");
                        author_text.setText("-"+myJsonObject.getString("author"));
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           },error -> {
           // below line is use to display a toast message along with our error.
           Toast.makeText(MainActivity2.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
       });
       q_queue.add(myRequest);
}
public void changeLocation(View view)
{
    Intent i=new Intent(MainActivity2.this,
            MainActivity3.class);
    //Intent is used to switch from one activity to another.

    startActivity(i);
    //invoke the SecondActivity.

    finish();
}

public void speaknow(View view)
{
    if(firstName.equals("Clear"))
    {
       s1="Hi"+user_name+"currently in"+city_name+"it's"+ x+"degree celsius with "+firstName+"skyys"+"So Carrying Umbrella is Not Advised";

    }
    else
      s1="Hi"+user_name+"currently in"+city_name+"it's"+ x+"degree celsius with "+firstName+"skyys"+"So Carrying Umbrella is Advised";

    t1.setSpeechRate(0.80f);
    t1.setPitch(0.90f);
    t1.speak(s1,TextToSpeech.QUEUE_FLUSH,null);
}
}
