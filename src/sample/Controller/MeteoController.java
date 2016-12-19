package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.text.Text;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import sample.Model.InfoMeteo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by axel on 19/12/2016.
 */
public class MeteoController {

    private String url = "http://www.infoclimat.fr/public-api/gfs/xml?_ll=43.94834,4.80892&_auth=ABpQRwB%2BUnAFKFViBnBXflM7BzJdK1RzA39VNl8xAn8CaANjVD9VNFc8VyoALwMzAi9TOwg3CDgDaQdiWigEeABgUDQAYFI1BWxVPwY3V3xTfwdlXWdUcwN%2FVTpfMAJ%2FAmMDY1QxVSlXP1c9AC4DNQIxUzoIKAgvA2EHY1ozBG8Aa1AyAGZSNAVpVTQGKVd8U2UHZ11kVGoDYFVmXzsCaAJoA2ZUMVU2V2lXPQAuAzQCM1MwCDQIMANoB2RaNQR4AHxQTQAQUi0FKlV1BmNXJVN9BzJdPFQ4&_c=f30d5acf5e18de4f029990712316a936";
    //private InfoMeteo meteo;


    @FXML
    public Button add;

    @FXML
    public Label temperatureAprem;

    @FXML
    public Text yolo;

    @FXML
    public void changeTemperature(){
       this.yolo.setText("20°");
       // System.out.println( meteo.getTemperature());

    }


    @FXML
    void initialize() {
        assert temperatureAprem != null : "fx:id=\"temperatureAprem\" was not injected: check your FXML file 'sample.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'sample.fxml'.";

    }

  /*  public MeteoController(){

        try {


           XMLReader xr = XMLReaderFactory.createXMLReader();
            SaxReader reader = new SaxReader();
            xr.setContentHandler(reader);


            URL monURL = new URL(url);
            URLConnection connexion = monURL.openConnection();
            InputStream flux = connexion.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(flux));
            xr.parse(new InputSource(in));
            InfoMeteo infos = reader.getInfo();
            /*this.countryField.setText(infos.getTemperature());
            this.regionField.setText(infos.getVille().getCoordonnees());
            this.latitudeField.setText(infos.getLatitude());
            this.longitudeField.setText(infos.getLongitude());
            this.timeZoneField.setText(infos.getTimeZone());
            //LOGGER.info(infos.getCityName());


        }catch(Exception ee){

        }

    } */

}
