package sample.Model;

import java.util.Date;
import java.util.Observable;

/**
 * Created by axel on 19/12/2016.
 */
public class InfoMeteo {

    protected String morningTemperature;
    protected String afTemperature;
    protected String city;
    protected String country;
    protected Date date;
    protected String morningImage;
    protected String afImage;



    public String getMorningTemperature() {

        return morningTemperature;
    }

    public void setMorningTemperature(String morningTemperature) {

        this.morningTemperature = morningTemperature;
    }

    public String getAfTemperature() {

        return afTemperature;
    }

    public void setAfTemperature(String afTemperature) {

        this.afTemperature = afTemperature;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMorningImage() {

        return morningImage;
    }

    public void setMorningImage(String morningImage) {
        this.morningImage = morningImage;
    }

    public String getAfImage() {
        return afImage;
    }

    public void setAfImage(String afImage) {
        this.afImage = afImage;
    }
}
