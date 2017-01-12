package sample.jfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
import sample.Controller.MainController;
import sample.Model.InfoMeteo;
import sample.Model.ReadFile;
import sample.Model.SaxReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by axel on 19/12/2016.
 */
public class MeteoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoController.class);

    //private String url = "http://www.infoclimat.fr/public-api/gfs/xml?_ll=43.94834,4.80892&_auth=ABpQRwB%2BUnAFKFViBnBXflM7BzJdK1RzA39VNl8xAn8CaANjVD9VNFc8VyoALwMzAi9TOwg3CDgDaQdiWigEeABgUDQAYFI1BWxVPwY3V3xTfwdlXWdUcwN%2FVTpfMAJ%2FAmMDY1QxVSlXP1c9AC4DNQIxUzoIKAgvA2EHY1ozBG8Aa1AyAGZSNAVpVTQGKVd8U2UHZ11kVGoDYFVmXzsCaAJoA2ZUMVU2V2lXPQAuAzQCM1MwCDQIMANoB2RaNQR4AHxQTQAQUi0FKlV1BmNXJVN9BzJdPFQ4&_c=f30d5acf5e18de4f0299";

    private String url = "http://api.openweathermap.org/data/2.5/forecast?q=quebec%2Cca&APPID=0062ef1b5a55a820248ae7cdda426cd0&mode=xml";

    private InfoMeteo meteo;

    private MainController controller;

    private PrintWriter out;

    private String result;

    private SaxReader pp = null;

    private int boolAction;

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
        System.out.println("clicked on " + bookmarks.getSelectionModel().getSelectedItem());

        this.controller.notifySearch(bookmarks.getSelectionModel().getSelectedItem());
        this.boolAction=1;

    }

    @FXML
    private void findCity(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Rechercher");
        dialog.setHeaderText("Rechercher une ville");
        dialog.setContentText("Entrer le nom de la ville");


        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            this.result =result.get();

        }

        this.controller.notifySearch(this.result);
        this.boolAction=1;

    }

    public void setImage(InfoMeteo meteo){
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


    @FXML
    private void next(){

        controller.notifyChangeJour(0);

    }

    @FXML
    private void last(){

        controller.notifyChangeJour(1);

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



    public void updateTextField(InfoMeteo day) {

        if ((Pattern.matches("Mon.*", day.getDate().toString()))) {
            date.setText("Monday");
        }
        if ((Pattern.matches("Tue.*", day.getDate().toString()))) {
            date.setText("Tuesday");
        }
        if ((Pattern.matches("Wed.*", day.getDate().toString()))) {
            date.setText("Wednesday");
        }
        if ((Pattern.matches("Thu.*", day.getDate().toString()))) {
            date.setText("Thursday");
        }
        if ((Pattern.matches("Fri.*", day.getDate().toString()))) {
            date.setText("Friday");
        }
        if ((Pattern.matches("Sat.*", day.getDate().toString()))) {
            date.setText("Saturday");
        }
        if ((Pattern.matches("Sun.*", day.getDate().toString()))) {
            date.setText("Sunday");
        }

        city.setText(day.getCity());
        float valueMorning = Float.parseFloat(day.getMorningTemperature());
        LOGGER.info(Float.toString(valueMorning));
        float valueAf = Float.parseFloat(day.getAfTemperature());
        LOGGER.info(Float.toString(valueAf));

        if(valueMorning > 100.0) {
            afTemperature.setText(Double.toString(valueAf - 273.15).substring(0,4));
        }else{
            afTemperature.setText(Double.toString(valueAf).substring(0,4));
        }
        if(valueAf > 100.0) {
            morningTemperature.setText(Double.toString(valueMorning - 273.15).substring(0,4) + "°");
        }else{
            morningTemperature.setText(Double.toString(valueMorning).substring(0,4) + "°");
        }

    }

    public void setController(MainController controller) {

        this.controller = controller;
    }

    public void updateSearch(SaxReader pp){

        this.controller.setIter(pp.tabDay.listIterator());
    }
}