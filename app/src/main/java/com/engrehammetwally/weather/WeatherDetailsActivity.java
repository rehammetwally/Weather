package com.engrehammetwally.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.engrehammetwally.weather.common.Common;
import com.squareup.picasso.Picasso;

public class WeatherDetailsActivity extends AppCompatActivity {
    TextView tvLastUpdate, tvDescription, tvMaxTemp, tvMinTemp, tvHumidity, tvPressure, tvWind;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        tvLastUpdate = findViewById(R.id.tvLastUpdate);
        tvDescription = findViewById(R.id.tvDescription);
        tvMaxTemp = findViewById(R.id.tvMaxTemp);
        tvMinTemp = findViewById(R.id.tvMinTemp);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvPressure = findViewById(R.id.tvPressure);
        tvWind = findViewById(R.id.tvWind);

        ivImage = findViewById(R.id.ivImage);

        if (getIntent() != null) {
            tvLastUpdate.setText(String.format("%s \n %s",getIntent().getStringExtra("TODAY"), getIntent().getStringExtra("DATE")));
            tvDescription.setText(String.format("%s", getIntent().getStringExtra("DESCRIPTION")));
            tvMaxTemp.setText(String.format("%.2s °C", getIntent().getDoubleExtra("MAXTEMP",0)));
            tvMinTemp.setText(String.format("%.2s °C", getIntent().getDoubleExtra("MINTEMP",0)));
            tvHumidity.setText(String.format("%s%%", getIntent().getIntExtra("HUMIDITY", 0)));
            tvPressure.setText(String.format("%s hPa", getIntent().getDoubleExtra("PRESSURE", 0)));
            tvWind.setText(String.format("%s km/h", getIntent().getDoubleExtra("WIND", 0)));

            Picasso.get()
                    .load(Common.getImage(getIntent().getStringExtra("IMAGE")))
                    .into(ivImage);

        }
    }
}
