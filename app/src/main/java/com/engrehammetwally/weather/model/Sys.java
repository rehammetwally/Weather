package com.engrehammetwally.weather.model;



public class Sys {
    private Double message,sunrise,sunset;
    private String country;


    public Sys(Double message, Double sunrise, Double sunset, String country) {
        this.message = message;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.country = country;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Double getSunrise() {
        return sunrise;
    }

    public void setSunrise(Double sunrise) {
        this.sunrise = sunrise;
    }

    public Double getSunset() {
        return sunset;
    }

    public void setSunset(Double sunset) {
        this.sunset = sunset;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
