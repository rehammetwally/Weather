package com.engrehammetwally.weather.model;


import java.util.List;

public class OpenWeatherMap {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private int dt,id,cod;
    private String name;

    public OpenWeatherMap() {
    }

    public OpenWeatherMap(Coord coord, List<Weather> weather, Main main, Wind wind) {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public OpenWeatherMap(List<Weather> weather, Main main, Wind wind) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public OpenWeatherMap(Coord coord, List<Weather> weather, String base, Main main, Wind wind, Clouds clouds, Sys sys, int dt, int id, int cod, String name) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.sys = sys;
        this.dt = dt;
        this.id = id;
        this.cod = cod;
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
