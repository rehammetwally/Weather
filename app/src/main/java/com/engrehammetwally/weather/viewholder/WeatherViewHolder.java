package com.engrehammetwally.weather.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.engrehammetwally.weather.R;
import com.engrehammetwally.weather.listener.ItemClickListener;


public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView ivIcon;
    public TextView tvWeatherDay,tvWeatherDesc,tvWeatherMin,tvWeatherMax;
    private ItemClickListener itemClickListener;

    public WeatherViewHolder(View itemView) {
        super(itemView);

        tvWeatherDay=itemView.findViewById(R.id.tvWeatherDay);
        tvWeatherDesc=itemView.findViewById(R.id.tvWeatherDesc);
        tvWeatherMin=itemView.findViewById(R.id.tvWeatherMin);
        tvWeatherMax=itemView.findViewById(R.id.tvWeatherMax);


        ivIcon=itemView.findViewById(R.id.ivIcon);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
