package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import sample.Model.InfoMeteo;
import sample.Model.ReadFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by axel on 19/12/2016.
 */
public class MeteoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoController.class);

    //private String url = "http://www.infoclimat.fr/public-api/gfs/xml?_ll=43.94834,4.80892&_auth=ABpQRwB%2BUnAFKFViBnBXflM7BzJdK1RzA39VNl8xAn8CaANjVD9VNFc8VyoALwMzAi9TOwg3CDgDaQdiWigEeABgUDQAYFI1BWxVPwY3V3xTfwdlXWdUcwN%2FVTpfMAJ%2FAmMDY1QxVSlXP1c9AC4DNQIxUzoIKAgvA2EHY1ozBG8Aa1AyAGZSNAVpVTQGKVd8U2UHZ11kVGoDYFVmXzsCaAJoA2ZUMVU2V2lXPQAuAzQCM1MwCDQIMANoB2RaNQR4AHxQTQAQUi0FKlV1BmNXJVN9BzJdPFQ4&_c=f30d5acf5e18de4f0299";

    private String url = "http://api.openweathermap.org/data/2.5/forecast?q=quebec%2Cca&APPID=0062ef1b5a55a820248ae7cdda426cd0&mode=xml";

    private InfoMeteo meteo;

    private PrintWriter out;

    private SaxReader pp = null;

    private int nbWindow = 0;

    private ArrayList<String> values;

    @FXML
    private ListView<String> bookmarks;

    @FXML
    private Text morningTemperature;

    @FXML
    private Text afTemperature;

    @FXML
    private Text city;

    @FXML
    private Text date;

    @FXML
    private ImageView morningImage;

    @FXML
    private ImageView afImage;

    @FXML
    private Button bookmarksBtn;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button lastButton;

    @FXML
    private Button nextButton;

    protected ListIterator<InfoMeteo> iter;

    private int day;

    public MeteoController() {
       // bookmarksInit();
        this.day = 0;

    }



    // Initialise les bookmarks
    @FXML
    public void bookmarksInit() {
        this.day = 0;
            System.out.print(bookmarksBtn.getText());
        if(bookmarksBtn.getText().equals("Bookmarks : OFF")) {
            add.setDisable(false);
            delete.setDisable(false);
            values = new ArrayList<String>();
            //  bookmarks = new ListView<>();
            ReadFile read = new ReadFile();
            read.loadBookmarks(this.values, "bookmarks.txt");
            System.out.println("mes valeurs :" + values);
            for (int i = 0; i < values.size(); i++) {
                setBookmarks(values.get(i));
            }
            // bookmarks.setItems(FXCollections.observableArrayList(values));
            // System.out.println(bookmarks.getItems());
            // setChanged();

            // notifyObservers(this.bookmarks);
            bookmarksBtn.setText("Bookmarks : ON");
        }else{
            bookmarks.getItems().clear();
            add.setDisable(true);
            delete.setDisable(true);
            bookmarksBtn.setText("Bookmarks : OFF");
        }

    }


    @FXML
    private void add() {

        this.day = 0;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add city");
        dialog.setHeaderText("Add your favorite city!");
        dialog.setContentText("Please enter a city name:");



        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {


            setBookmarks(result.get());
            LOGGER.info("Ca passe");
            try (FileWriter fw = new FileWriter("bookmarks.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(result.get());



            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }

    }

    @FXML
    private void delete() {
        ReadFile read = new ReadFile();
        read.deleteItem(bookmarks.getSelectionModel().getSelectedItem(), "bookmarks.txt");
        System.out.println("clicked on " + bookmarks.getSelectionModel().getSelectedItem());

        bookmarks.getItems().remove(bookmarks.getSelectionModel().getSelectedItem());

    }

    @FXML public void handleMouseClick(MouseEvent arg0) {
        this.day = 0;
        this.lastButton.setVisible(false);
        this.nextButton.setVisible(true);
        System.out.println("clicked on " + bookmarks.getSelectionModel().getSelectedItem());
        this.url = "http://api.openweathermap.org/data/2.5/forecast?q=" + bookmarks.getSelectionModel().getSelectedItem() + "%2Cfr&APPID=0062ef1b5a55a820248ae7cdda426cd0&mode=xml";
        parse(url);
        this.iter = pp.tabDay.listIterator();
        meteo = new InfoMeteo();
        meteo = iter.next();
        //meteo = pp.tabDay.get(0);

        float valueMorning = Float.parseFloat(meteo.getMorningTemperature());
        LOGGER.info(Float.toString(valueMorning));
        float valueAf = Float.parseFloat(meteo.getAfTemperature());
        LOGGER.info(Float.toString(valueAf));

        if(valueMorning > 100.0) {
            afTemperature.setText(Double.toString(valueAf - 273.15).substring(0,4));
        }else{
            afTemperature.setText(Double.toString(valueAf).substring(0,4));
        }
        if(valueAf > 100.0) {
            morningTemperature.setText(Double.toString(valueMorning - 273.15).substring(0,4));
        }else{
            morningTemperature.setText(Double.toString(valueMorning).substring(0,4));
        }


        city.setText(meteo.getCity());
        date.setText(meteo.getDate().toString());
        LOGGER.info(meteo.getAfTemperature());
        LOGGER.info(meteo.getMorningTemperature());
        LOGGER.info(meteo.getCity());
        LOGGER.info(meteo.getAfImage());
        LOGGER.info(meteo.getMorningImage());
        LOGGER.info(meteo.getDate().toString());
        setImage(meteo);

    }

    @FXML
    private void findCity(){
        TextInputDialog dialog = new TextInputDialog("");
        TextInputDialog answer = new TextInputDialog("");
        dialog.setTitle("Select your city");
        dialog.setHeaderText("Select your favorite city!");
        dialog.setContentText("Please enter a city name:");


        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            this.url = "http://api.openweathermap.org/data/2.5/forecast?q=" + result.get() + "%2Cfr&APPID=0062ef1b5a55a820248ae7cdda426cd0&mode=xml";
            parse(url);
            this.iter = pp.tabDay.listIterator();
            meteo = new InfoMeteo();
            meteo = iter.next();
            //meteo = pp.tabDay.get(0);

            float valueMorning = Float.parseFloat(meteo.getMorningTemperature());
            LOGGER.info(Float.toString(valueMorning));
            float valueAf = Float.parseFloat(meteo.getAfTemperature());
            LOGGER.info(Float.toString(valueAf));

            if(valueMorning > 100.0) {
                afTemperature.setText(Double.toString(valueAf - 273.15).substring(0,4));
            }else{
                afTemperature.setText(Double.toString(valueAf).substring(0,4));
            }

            city.setText(meteo.getCity());
            date.setText(meteo.getDate().toString());
            LOGGER.info(meteo.getAfTemperature());
            LOGGER.info(meteo.getMorningTemperature());
            LOGGER.info(meteo.getCity());
            LOGGER.info(meteo.getAfImage());
            LOGGER.info(meteo.getMorningImage());
            LOGGER.info(meteo.getDate().toString());
            setImage(meteo);
        }
    }

    private void setImage(InfoMeteo meteo){
        String urlimgmatin=null;

        if(meteo.getMorningImage().equals("clear sky")){
            urlimgmatin="soleil.png";

        }
        else if((meteo.getMorningImage().equals("few clouds"))||(meteo.getMorningImage().equals("broken clouds"))){
            urlimgmatin="nuage.png";

        }
        else if((meteo.getMorningImage().equals("scattered clouds"))||(meteo.getMorningImage().equals("overcast clouds"))){
            urlimgmatin="nuageux.png";

        }
        else if((meteo.getMorningImage().equals("rain"))||(meteo.getMorningImage().equals("light rain"))){
            urlimgmatin="pluie.png";
        }
        else if((meteo.getMorningImage().equals("snow"))||(meteo.getMorningImage().equals("light snow"))){
            urlimgmatin="neige.png";
        }
        else{
            urlimgmatin="eclair.png";
        }



        String urlimgAprem=null;
        if (meteo.getAfTemperature()!=null) {

            if (meteo.getAfImage().equals("clear sky")) {
                urlimgAprem = "soleil.png";

            } else if ((meteo.getAfImage().equals("few clouds")) || (meteo.getAfImage().equals("broken clouds"))) {
                urlimgAprem = "nuage.png";

            } else if ((meteo.getAfImage().equals("scattered clouds")) || (meteo.getAfImage().equals("overcast clouds"))) {
                urlimgAprem = "nuageux.png";

            } else if ((meteo.getAfImage().equals("rain")) || (meteo.getAfImage().equals("light rain"))) {
                urlimgAprem = "pluie.png";
            } else if ((meteo.getAfImage().equals("snow")) || (meteo.getAfImage().equals("light snow"))) {
                urlimgAprem = "neige.png";
            } else {
                urlimgAprem = "eclair.png";
            }

            try {


                File file = new File("src/sample/images/" + urlimgmatin);
                Image imageMatin = new Image(file.toURI().toString());
                morningImage.setImage(imageMatin);

                File file2 = new File("src/sample/images/" + urlimgAprem);
                Image imageAprem = new Image(file2.toURI().toString());
                afImage.setImage(imageAprem);

            } catch (Exception e) {

            }


        }

    }

private void convert() {

    if(meteo != null) {
        double d = Double.parseDouble(meteo.getAfTemperature());
        int i = new Double(d).intValue();
        double decimale = d - (new Double(i).doubleValue());
        if (decimale > 0.5) {
            int temp = Integer.parseInt(meteo.getAfTemperature()) + 1;
            String temp2 = String.valueOf(temp);
            meteo.setAfTemperature(temp2);

        } else {
            int temp = Integer.parseInt(meteo.getAfTemperature());
            String temp2 = String.valueOf(temp);
            meteo.setAfTemperature(temp2);
        }

        d = Double.parseDouble(meteo.getMorningTemperature());
        i = new Double(d).intValue();
        decimale = d - (new Double(i).doubleValue());
        if (decimale > 0.5) {
            int temp = Integer.parseInt(meteo.getMorningTemperature()) + 1;
            String temp2 = String.valueOf(temp);
            meteo.setMorningTemperature(temp2);

        } else {
            int temp = Integer.parseInt(meteo.getMorningTemperature());
            String temp2 = String.valueOf(temp);
            meteo.setMorningTemperature(temp2);
        }
    }
}

    @FXML
    private void next(){

        if(meteo != null) {
            this.lastButton.setVisible(true);
            if (this.day == 3) {
                this.nextButton.setVisible(false);
            }
            meteo = iter.next();
            float valueMorning = Float.parseFloat(meteo.getMorningTemperature());
            LOGGER.info(Float.toString(valueMorning));
            float valueAf = Float.parseFloat(meteo.getAfTemperature());
            LOGGER.info(Float.toString(valueAf));

            if (valueMorning > 100.0) {
                afTemperature.setText(Double.toString(valueAf - 273.15).substring(0, 4));
            } else {
                afTemperature.setText(Double.toString(valueAf).substring(0, 4));
            }
            if (valueAf > 100.0) {
                morningTemperature.setText(Double.toString(valueMorning - 273.15).substring(0, 4));
            } else {
                morningTemperature.setText(Double.toString(valueMorning).substring(0, 4));
            }


            city.setText(meteo.getCity());
            date.setText(meteo.getDate().toString());
            setImage(meteo);
            this.day++;
            LOGGER.info(Integer.toString(this.day));


        }
    }

    @FXML
    private void last(){

            this.nextButton.setVisible(true);
            if(this.day == 0){
                this.lastButton.setVisible(false);
            }
            meteo = iter.previous();
           float valueMorning = Float.parseFloat(meteo.getMorningTemperature());
            LOGGER.info(Float.toString(valueMorning));
            float valueAf = Float.parseFloat(meteo.getAfTemperature());
            LOGGER.info(Float.toString(valueAf));

            if (valueMorning > 100.0) {
                afTemperature.setText(Double.toString(valueAf - 273.15).substring(0,4));
            } else {
                afTemperature.setText(Double.toString(valueAf).substring(0,4));
            }
            if (valueAf > 100.0) {
                morningTemperature.setText(Double.toString(valueMorning - 273.15).substring(0,4));
            } else {
                morningTemperature.setText(Double.toString(valueMorning).substring(0,4));
            }



            city.setText(meteo.getCity());
            date.setText(meteo.getDate().toString());
            setImage(meteo);
            this.day--;
            LOGGER.info(Integer.toString(this.day));


    }


    public String getBookmarksItem(int i) {
        return bookmarks.getItems().get(i);
    }

    public void setBookmarks(String value) {
       bookmarks.getItems().add(bookmarks.getItems().size(), value);

    }

    public int getNbWindow() {
        return nbWindow;
    }

    public void setNbWindow(int nbWindow) {
        this.nbWindow = nbWindow;
    }

    public ListView<String> getBookmarks() {
        return bookmarks;
    }

    public void parse(String url){
        HttpURLConnection connexion = null;

        URL monURL = null;
        try {

            monURL = new URL(url);
        } catch (MalformedURLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        LOGGER.info("Connexion a l'url ...");
        try {
            connexion= (HttpURLConnection) monURL.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        LOGGER.info(connexion.getRequestMethod());
        try {
            LOGGER.info(connexion.getResponseMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(connexion.getHeaderFields());
        BufferedReader in = null;
        try {
            in = new BufferedReader( new InputStreamReader(connexion.getInputStream()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        XMLReader xr=null;;
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        pp=new SaxReader();
        xr.setContentHandler(pp);
        try {
            xr.parse( new InputSource(in));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //System.out.println("infoip"+pp.info);


        connexion.disconnect();
    }
}