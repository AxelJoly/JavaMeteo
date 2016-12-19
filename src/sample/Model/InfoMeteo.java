package sample.Model;

import java.util.Date;

/**
 * Created by axel on 19/12/2016.
 */
public class InfoMeteo {

    private String temperature;
    private Ville ville;
    private String date;
    private String meteoLogo;

    public InfoMeteo(){
        this.temperature = "yolo";
    }



    public String getTemperature() {
       int value = Integer.parseInt(this.temperature);
        value = value - 275;
        return Integer.toString(value);
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeteoLogo() {
        return meteoLogo;
    }

    public void setMeteoLogo(String meteoLogo) {
        this.meteoLogo = meteoLogo;
    }
}
