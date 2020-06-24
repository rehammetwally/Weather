package com.engrehammetwally.weather.model;


public class WeatherObject {

    private City city;
    private int cod, cnt;
    private double message;
    private java.util.List<List> list;

    public WeatherObject() {
    }

    public WeatherObject(City city, java.util.List<List> list) {
        this.city = city;
        this.list = list;
    }

    public WeatherObject(java.util.List<List> list) {
        this.list = list;
    }

    public WeatherObject(City city, int cod, int cnt, double message, java.util.List<List> list) {
        this.city = city;
        this.cod = cod;
        this.cnt = cnt;
        this.message = message;
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }
}
