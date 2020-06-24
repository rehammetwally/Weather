package com.engrehammetwally.weather.model;


public class List {
    private int dt;
    private Main main;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Sys sys;

    private java.util.List<Weather> weather;
    private String dt_txt;

    public List() {
    }

    public List(Main main, Wind wind, java.util.List<Weather> weather, String dt_txt) {
        this.main = main;
        this.wind = wind;
        this.weather = weather;
        this.dt_txt = dt_txt;
    }

    public List(int dt, Main main, Clouds clouds, Wind wind, Rain rain, Sys sys, java.util.List<Weather> weather, String dt_txt) {
        this.dt = dt;
        this.main = main;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.sys = sys;
        this.weather = weather;
        this.dt_txt = dt_txt;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
