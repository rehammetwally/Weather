package com.engrehammetwally.weather.model;



public class City {
    private int id,population;
    private String name,country;
    private Coord coord;


    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public City(int id, int population, String name, String country, Coord coord) {
        this.id = id;
        this.population = population;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
