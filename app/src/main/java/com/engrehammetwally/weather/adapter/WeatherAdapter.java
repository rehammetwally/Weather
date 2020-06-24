package com.engrehammetwally.weather.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.engrehammetwally.weather.R;
import com.engrehammetwally.weather.WeatherDetailsActivity;
import com.engrehammetwally.weather.common.Common;
import com.engrehammetwally.weather.listener.ItemClickListener;
import com.engrehammetwally.weather.model.WeatherObject;
import com.engrehammetwally.weather.viewholder.WeatherViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
    private Context context;
    private List<WeatherObject> dailyWeather;

    public WeatherAdapter(Context context, List<WeatherObject> dailyWeather) {
        this.context = context;
        this.dailyWeather = dailyWeather;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.weather_list_item, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        holder.tvWeatherDay.setText(String.format("%s", dailyWeather.get(position).getList().get(0).getDt_txt()));
        holder.tvWeatherDesc.setText(String.format("%s", dailyWeather.get(position).getList().get(0).getWeather().get(0).getDescription()));
        holder.tvWeatherMin.setText(String.format("%.2s °C", dailyWeather.get(position).getList().get(0).getMain().getTemp_max()));
        holder.tvWeatherMax.setText(String.format("%.2s °C", dailyWeather.get(position).getList().get(0).getMain().getTemp_min()));

        Picasso.get()
                .load(Common.getImage(dailyWeather.get(position).getList().get(0).getWeather().get(0).getIcon()))
                .into(holder.ivIcon);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context, WeatherDetailsActivity.class);
                intent.putExtra("TODAY", Common.getDayName(dailyWeather.get(position).getList().get(0).getDt_txt()));
                intent.putExtra("DATE", Common.getDate(dailyWeather.get(position).getList().get(0).getDt_txt()));
                intent.putExtra("HUMIDITY", dailyWeather.get(position).getList().get(0).getMain().getHumidity());
                intent.putExtra("PRESSURE", dailyWeather.get(position).getList().get(0).getMain().getPressure());
                intent.putExtra("WIND", dailyWeather.get(position).getList().get(0).getWind().getSpeed());
                intent.putExtra("DESCRIPTION", dailyWeather.get(position).getList().get(0).getWeather().get(0).getDescription());
                intent.putExtra("MINTEMP", dailyWeather.get(position).getList().get(0).getMain().getTemp_min());
                intent.putExtra("MAXTEMP", dailyWeather.get(position).getList().get(0).getMain().getTemp_max());
                intent.putExtra("IMAGE", dailyWeather.get(position).getList().get(0).getWeather().get(0).getIcon());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }

    public void clear() {
        int size = this.dailyWeather.size();
        this.dailyWeather.clear();
        notifyItemRangeRemoved(0, size);
    }
}
