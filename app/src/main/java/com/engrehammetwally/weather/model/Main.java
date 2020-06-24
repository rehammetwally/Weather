package com.engrehammetwally.weather.model;



public class Main {
    private double temp,pressure,temp_min,temp_max,sea_level,grnd_level;
    private int humidity;

    public Main() {
    }

    public Main(double temp_min, double temp_max) {
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public Main(double pressure, double temp_min, double temp_max, int humidity) {
        this.pressure = pressure;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
    }

    public Main(double temp, double pressure, double temp_min, double temp_max, double sea_level, double grnd_level, int humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public void setSea_level(double sea_level) {
        this.sea_level = sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(double grnd_level) {
        this.grnd_level = grnd_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}

