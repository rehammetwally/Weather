package com.engrehammetwally.weather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.engrehammetwally.weather.common.Common;
import com.engrehammetwally.weather.model.City;
import com.engrehammetwally.weather.model.Coord;
import com.engrehammetwally.weather.model.Main;
import com.engrehammetwally.weather.model.OpenWeatherMap;
import com.engrehammetwally.weather.model.Weather;
import com.engrehammetwally.weather.model.WeatherObject;
import com.engrehammetwally.weather.model.Wind;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteAssetHelper {
    private static String DATABASE_NAME = "weatherDB.db";
    private static int DATABASE_VERSION = 1;


    public Database(Context context) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }


    public void addToOpenWeatherMap(OpenWeatherMap openWeatherMap) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(
                "UPDATE openWeatherMap SET" +
                        " description = '" + openWeatherMap.getWeather().get(0).getDescription() + "'," +
                        " maxTemp = '" + openWeatherMap.getMain().getTemp_max() + "'," +
                        " minTemp = '" + openWeatherMap.getMain().getTemp_min() + "'," +
                        " image = '" + Common.getImage(openWeatherMap.getWeather().get(0).getIcon()) + "'," +
                        " humidity = '" + openWeatherMap.getMain().getHumidity() + "'," +
                        " pressure = '" + openWeatherMap.getMain().getPressure() + "'," +
                        " wind = '" + openWeatherMap.getWind().getSpeed() + "'," +
                        " lat = '" + openWeatherMap.getCoord().getLat() + "'," +
                        " lng = '" + openWeatherMap.getCoord().getLon() + "' WHERE id = 0"
        );
        db.execSQL(query);
        db.close();

    }

    public long addToWeatherObject(List<WeatherObject> weatherObject) {
        SQLiteDatabase db = getReadableDatabase();
//        db.delete("weatherObject",null,null);

//        String whereClause = "city=? AND country=?";

        long update = 0;
//        String whereArgs[]= {weatherObject.get(0).getCity().getName(), weatherObject.get(0).getCity().getCountry()};

        ContentValues values = new ContentValues();
        for (int i = 0; i <weatherObject.size() ; i++) {
            values.put("description", weatherObject.get(i).getList().get(0).getWeather().get(0).getDescription());
            values.put("maxTemp",String.format("%.2s °C", weatherObject.get(i).getList().get(0).getMain().getTemp_max()));
            values.put("minTemp", String.format("%.2s °C", weatherObject.get(i).getList().get(0).getMain().getTemp_min()));
            values.put("image", weatherObject.get(i).getList().get(0).getWeather().get(0).getIcon());
            values.put("date", weatherObject.get(i).getList().get(0).getDt_txt());
            values.put("humidity", weatherObject.get(i).getList().get(0).getMain().getHumidity());
            values.put("pressure", weatherObject.get(i).getList().get(0).getMain().getPressure());
            values.put("wind", weatherObject.get(i).getList().get(0).getWind().getSpeed());
            update =  db.update("weatherObject",values,"id="+i, null);
        }


        db.close();
        return update;
    }

    public OpenWeatherMap getWeatherMap() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"description", "maxTemp", "minTemp", "image", "humidity", "pressure", "wind", "lat", "lng"};
        String sqlTable = "openWeatherMap";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        OpenWeatherMap result = null;
        if (c.moveToFirst()) {
            do {
                List<Weather> weathers = new ArrayList<>();
                weathers.add(
                        new Weather(
                                c.getString(c.getColumnIndex("description")),
                                c.getString(c.getColumnIndex("image"))
                        )
                );
                result = new OpenWeatherMap(
                        new Coord(
                                c.getDouble(c.getColumnIndex("lat")),
                                c.getDouble(c.getColumnIndex("lng"))
                        ),
                        weathers,
                        new Main(
                                c.getInt(c.getColumnIndex("pressure")),
                                c.getDouble(c.getColumnIndex("minTemp")),
                                c.getDouble(c.getColumnIndex("maxTemp")),
                                c.getInt(c.getColumnIndex("humidity"))
                        ),
                        new Wind(c.getDouble(c.getColumnIndex("wind")))
                );

            } while (c.moveToNext());
        }
        db.close();
        return result;
    }


    public List<WeatherObject> getWeatherList() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"description", "image", "maxTemp", "minTemp", "date","humidity","pressure","wind"};
        String sqlTable = "weatherObject";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        List<WeatherObject> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                List<Weather> weathers = new ArrayList<>();
                weathers.add(
                        new Weather(
                                c.getString(c.getColumnIndex("description")),
                                c.getString(c.getColumnIndex("image"))
                        )
                );
                List<com.engrehammetwally.weather.model.List> lists = new ArrayList<>();
                lists.add(
                        new com.engrehammetwally.weather.model.List(
                                new Main(
                                        c.getDouble(c.getColumnIndex("pressure")),
                                        c.getDouble(c.getColumnIndex("maxTemp")),
                                        c.getDouble(c.getColumnIndex("minTemp")),
                                        c.getInt(c.getColumnIndex("humidity"))
                                ),
                                new Wind(c.getDouble(c.getColumnIndex("wind"))),
                                weathers,
                                c.getString(c.getColumnIndex("date"))
                        )
                );
                result.add(
                        new WeatherObject(
                                lists
                        )
                );
            } while (c.moveToNext());
        }
        db.close();
        return result;
    }

    public int countAll() {
        String countQuery = "SELECT  * FROM weatherObject";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        return cnt;
    }

    public void deleteWeather(){
//        String deleteQuery = "DELETE  * FROM weatherObject";
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(deleteQuery, null);
//        cursor.close();
        String TABLE_NAME="weatherObject";

        db.execSQL("DELETE  FROM weatherObject");
        db.close();
    }
}
