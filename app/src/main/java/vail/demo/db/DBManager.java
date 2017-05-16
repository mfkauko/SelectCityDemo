package vail.demo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vail.demo.model.City;

/**
 * Created by VailWei on 2017/5/4/004.
 */

public class DBManager {

    private String DB_NAME = "City.db3";
    private Context mContext;

    public DBManager(Context mContext) {
        this.mContext = mContext;
    }
    //把assets目录下的db文件复制到dbpath下
    public SQLiteDatabase initDataBaseManager(String packName) {
//        String dbDir = mContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        String dbDir = "/data/data/" + packName + "/databases/";
        File dbRoot = new File(dbDir);
        if(!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        String dbPath = dbDir + DB_NAME;
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(dbPath);
            InputStream in = mContext.getAssets().open(DB_NAME);
            byte[] buffer = new byte[1024];
            int readBytes = 0;
            while ((readBytes = in.read(buffer)) != -1)
                out.write(buffer, 0, readBytes);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

//    //查询单个City
//    public City query(SQLiteDatabase sqliteDB, String[] columns, String selection, String[] selectionArgs) {
//        City city = null;
//        try {
//            String table = "City";
//            Cursor cursor = sqliteDB.query(table, columns, selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                String cityName = cursor.getString(cursor.getColumnIndex("Name"));
//                String pinyin = cursor.getString(cursor.getColumnIndex("PinYin"));
//                String cityID = cursor.getString(cursor.getColumnIndex("CityCode"));
//                String letter = cursor.getString(cursor.getColumnIndex("Letter"));
//                String firstLetter = cursor.getString(cursor.getColumnIndex("FirstLetter"));
//                city = new City(cityID, cityName, letter, firstLetter, pinyin);
//                cursor.moveToNext();
//                cursor.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return city;
//    }

    //查询单个City
    public List<City> query(SQLiteDatabase sqliteDB, String[] columns, String selection, String[] selectionArgs) {
        List<City> cityList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String table = "City";
            cursor = sqliteDB.query(table, columns, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                String cityName = cursor.getString(cursor.getColumnIndex("Name"));
                String pinyin = cursor.getString(cursor.getColumnIndex("PinYin"));
                String cityID = cursor.getString(cursor.getColumnIndex("CityCode"));
                String letter = cursor.getString(cursor.getColumnIndex("Letter"));
                String firstLetter = cursor.getString(cursor.getColumnIndex("FirstLetter"));
                City city = new City(cityID, cityName, letter, firstLetter, pinyin);
                cityList.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return cityList;
    }

    public List<City> getAllCity(SQLiteDatabase sqliteDB) {
        String orderBy = "Letter ASC";
        List<City> cityList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String table = "City";
            cursor = sqliteDB.query(table, null, null, null, null, null, orderBy);
            while(cursor.moveToNext()) {
                String cityName = cursor.getString(cursor.getColumnIndex("Name"));
                String pinyin = cursor.getString(cursor.getColumnIndex("PinYin"));
                String cityID = cursor.getString(cursor.getColumnIndex("CityCode"));
                String letter = cursor.getString(cursor.getColumnIndex("Letter"));
                String firstLetter = cursor.getString(cursor.getColumnIndex("FirstLetter"));
                City city = new City(cityID, cityName, letter, firstLetter, pinyin);
                cityList.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return cityList;
    }

}
