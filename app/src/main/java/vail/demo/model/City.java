package vail.demo.model;

/**
 * Created by VailWei on 2017/5/4/004.
 */

public class City {

    private String pinyin;
    private String cityID;
    private String cityName;
    private String firstLetter;
    private String letter;

    public City(String cityID, String cityName, String letter, String firstLetter, String pinyin) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.letter = letter;
        this.firstLetter = firstLetter;
        this.pinyin = pinyin;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
