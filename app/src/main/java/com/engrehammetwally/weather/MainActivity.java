package com.engrehammetwally.weather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.engrehammetwally.weather.adapter.WeatherAdapter;
import com.engrehammetwally.weather.common.Common;
import com.engrehammetwally.weather.database.Database;
import com.engrehammetwally.weather.helper.Helper;
import com.engrehammetwally.weather.model.Coord;
import com.engrehammetwally.weather.model.Main;
import com.engrehammetwally.weather.model.OpenWeatherMap;
import com.engrehammetwally.weather.model.Weather;
import com.engrehammetwally.weather.model.WeatherObject;
import com.engrehammetwally.weather.model.Wind;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.time.OffsetDateTime.now;

public class MainActivity extends AppCompatActivity implements LocationListener, ConnectivityReceiver.ConnectivityReceiverListener {

    TextView tvToday, tvDescription, tvMaxTemp, tvMinTemp;
    ImageView ivImage;

    AppBarLayout appBarLayout;

    LocationManager locationManager;
    Location location = null;

    String provider;
    static double lat, lng;

    int MY_PERMISSION = 0;

    String today, dateNow, description, icon;
    double maxTemp, minTemp, pressure, wind;
    int humidity;

    RecyclerView rvWeatherList;
    RecyclerView.LayoutManager layoutManager;

    WeatherAdapter adapter;

    List<WeatherObject> dailyWeather = new ArrayList<>();
    WeatherObject weather = new WeatherObject();
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        if (checkConnection()) {
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
            if (location == null) {
                new WeatherTask().execute(Common.apiRequest(String.valueOf(30.0626), String.valueOf(31.2497)));

                new WeatherDailyTask().execute(Common.apiDailyRequest(String.valueOf(30.0626), String.valueOf(31.2497)));
            } else {
                new WeatherTask().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));

                new WeatherDailyTask().execute(Common.apiDailyRequest(String.valueOf(lat), String.valueOf(lng)));
            }

        } else {
            Toast.makeText(this, "Connect To internet to update weather data !!!", Toast.LENGTH_LONG).show();
            openWeatherMap = new Database(this).getWeatherMap();
            showDataFrom(openWeatherMap);
            dailyWeather = new Database(this).getWeatherList();
            showDailyWeatherFrom(dailyWeather);
        }


        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherDetailsActivity.class);
                intent.putExtra("TODAY", Common.getDay());
                intent.putExtra("DATE", Common.getDateNow());
                intent.putExtra("HUMIDITY", openWeatherMap.getMain().getHumidity());
                intent.putExtra("PRESSURE", openWeatherMap.getMain().getPressure());
                intent.putExtra("WIND", openWeatherMap.getWind().getSpeed());
                intent.putExtra("DESCRIPTION", openWeatherMap.getWeather().get(0).getDescription());
                intent.putExtra("MINTEMP", openWeatherMap.getMain().getTemp_min());
                intent.putExtra("MAXTEMP", openWeatherMap.getMain().getTemp_max());
                intent.putExtra("IMAGE", openWeatherMap.getWeather().get(0).getIcon());
                startActivity(intent);
            }
        });
    }


    private void init() {
        tvToday = findViewById(R.id.tvToday);
        tvDescription = findViewById(R.id.tvDescription);
        tvMaxTemp = findViewById(R.id.tvMaxTemp);
        tvMinTemp = findViewById(R.id.tvMinTemp);
        ivImage = findViewById(R.id.ivImage);
        rvWeatherList = findViewById(R.id.rvWeatherList);

        appBarLayout = findViewById(R.id.appBarLayout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);

        }

        try {
            location = locationManager.getLastKnownLocation(provider);
        } catch (RuntimeException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    private void showDailyWeatherFrom(java.util.List<WeatherObject> dailyWeather) {
        rvWeatherList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWeatherList.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(MainActivity.this, dailyWeather);
        rvWeatherList.setAdapter(adapter);
    }

    private void showDataFrom(OpenWeatherMap openWeatherMap) {
        today = Common.getDay();
        dateNow = Common.getDateNow();
        description = openWeatherMap.getWeather().get(0).getDescription();
        maxTemp = (int) openWeatherMap.getMain().getTemp_max();
        minTemp = (int) openWeatherMap.getMain().getTemp_min();
        icon = Common.getImage(openWeatherMap.getWeather().get(0).getIcon());
        humidity = openWeatherMap.getMain().getHumidity();
        pressure = openWeatherMap.getMain().getPressure();
        wind = openWeatherMap.getWind().getSpeed();


        tvToday.setText(today);
        tvDescription.setText(description);
        tvMaxTemp.setText(String.format("%.2s °C", maxTemp));
        tvMinTemp.setText(String.format("%.2s °C", minTemp));
        Picasso.get()
                .load(icon)
                .into(ivImage);

    }


    private boolean checkConnection() {
        MyApplication.getInstance().setConnectivityListener(MainActivity.this);
        return ConnectivityReceiver.isConnected();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);

        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);

        }
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (RuntimeException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        if (checkConnection()) {
            Toast.makeText(this, "Internet Connected !!!", Toast.LENGTH_SHORT).show();
            new WeatherTask().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
            new WeatherDailyTask().execute(Common.apiDailyRequest(String.valueOf(lat), String.valueOf(lng)));
        } else {
            Toast.makeText(this, "Connect to Internet To update Weather Data !!!", Toast.LENGTH_SHORT).show();
            openWeatherMap = new Database(this).getWeatherMap();
            showDataFrom(openWeatherMap);
            dailyWeather = new Database(this).getWeatherList();
            showDailyWeatherFrom(dailyWeather);
        }


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            Toast.makeText(MainActivity.this, "Internet Connected !!!", Toast.LENGTH_SHORT).show();
            new WeatherTask().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
            adapter.clear();
            new WeatherDailyTask().execute(Common.apiDailyRequest(String.valueOf(lat), String.valueOf(lng)));
        } else {
            Toast.makeText(MainActivity.this, "Connect to Internet To update Weather Data !!!", Toast.LENGTH_SHORT).show();
            openWeatherMap = new Database(MainActivity.this).getWeatherMap();
            showDataFrom(openWeatherMap);
            dailyWeather = new Database(MainActivity.this).getWeatherList();
            showDailyWeatherFrom(dailyWeather);
        }
    }

    public class WeatherTask extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Wait ...");
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];
            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.contains("Error: Not found city")) {
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>() {
            }.getType();
            openWeatherMap = gson.fromJson(s, type);
            pd.dismiss();

            showDataFrom(openWeatherMap);

            List<Weather> we = new ArrayList<>();
            we.add(
                    new Weather(
                            openWeatherMap.getWeather().get(0).getDescription(),
                            openWeatherMap.getWeather().get(0).getIcon()
                    )
            );
            new Database(MainActivity.this).addToOpenWeatherMap(new OpenWeatherMap(
                    new Coord(openWeatherMap.getCoord().getLat(), openWeatherMap.getCoord().getLon()),
                    we,
                    new Main(

                            openWeatherMap.getMain().getPressure(),
                            openWeatherMap.getMain().getTemp_min(),
                            openWeatherMap.getMain().getTemp_max(),
                            openWeatherMap.getMain().getHumidity()
                    ),
                    new Wind(openWeatherMap.getWind().getSpeed())
            ));
        }

    }

    public class WeatherDailyTask extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Wait ...");
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];
            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")) {
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<WeatherObject>() {
            }.getType();
            weather = gson.fromJson(s, type);
            for (int j = 0; j < weather.getList().size(); j++) {
                List<com.engrehammetwally.weather.model.List> listOfList = new ArrayList<>();
                List<Weather> listOfWeather = new ArrayList<>();
                String[] splited = weather.getList().get(j).getDt_txt().split("\\s+");
                String date = null;

                if (
                        splited[1].equals("00:00:00")
                                ||
                                Common.getDateNow().equals(splited[0])
                                        &&
                                        Common.getDateNow().compareTo(splited[1]) == -1
                        ) {
                    if (Common.getDateNow().equals(splited[0]) && Common.getDateNow().compareTo(splited[1]) == -1) {
                        date = "Today";
//                        List<Weather> w=new ArrayList<>();
//                        w.add(
//                                new Weather(
//                                        weather.getList().get(0).getWeather().get(0).getDescription(),
//                                        weather.getList().get(0).getWeather().get(0).getIcon()
//                                )
//                        );
//                        showDataFrom(
//                                new OpenWeatherMap(
//                                        new Coord(
//                                                weather.getCity().getCoord().getLat(),
//                                                weather.getCity().getCoord().getLon()
//                                        ),
//                                        w,
//                                        new Main(
//                                                weather.getList().get(0).getMain().getPressure(),
//                                                weather.getList().get(0).getMain().getTemp_min(),
//                                                weather.getList().get(0).getMain().getTemp_max(),
//                                                weather.getList().get(0).getMain().getHumidity()
//
//                                        ),
//                                        new Wind(weather.getList().get(0).getWind().getSpeed())
//
//                                )
//                        );
                    } else if (splited[0].equals(Common.getTomorrow())) {
                        date = "Tomorrow";
                    } else {
                        date = splited[0];
                    }
                    humidity = weather.getList().get(j).getMain().getHumidity();
                    pressure = weather.getList().get(j).getMain().getPressure();
                    wind=weather.getList().get(j).getWind().getSpeed();
                    description=weather.getList().get(j).getWeather().get(0).getDescription();
                    icon=weather.getList().get(j).getWeather().get(0).getIcon();
                    minTemp=weather.getList().get(j).getMain().getTemp_min();
                    maxTemp=weather.getList().get(j).getMain().getTemp_max();


                    listOfWeather.add(
                            new Weather(
                                    description,
                                    icon
                            )
                    );
                    listOfList.add(
                            new com.engrehammetwally.weather.model.List(
                                    new Main(
                                            pressure,
                                            minTemp,
                                            maxTemp,
                                            humidity
                                    ),
                                    new Wind(wind),
                                    listOfWeather,
                                    date
                            )
                    );

                    dailyWeather.add(
                            new WeatherObject(
                                    listOfList
                            )
                    );
                    new Database(MainActivity.this).addToWeatherObject(dailyWeather);
                }
            }

            showDailyWeatherFrom(dailyWeather);
            pd.dismiss();
        }


    }
}

