package sample.Model;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

/**
 * Created by axel on 11/01/2017.
 */
public class Result extends Observable{

    String ville;
    SaxReader pp= null;


    public void parse(String ville) {
        this.ville = ville;
        HttpURLConnection connexion = null;

        URL monURL = null;

        try {


            monURL = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + this.ville + "%2Cfr&APPID=0bd2ccefc77a0e59f11b2d8215a1fc76&mode=xml");


        } catch (MalformedURLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        System.out.println("Connexion a l'url ...");
        try {
            connexion = (HttpURLConnection) monURL.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(connexion.getRequestMethod());
        try {
            System.out.println(connexion.getResponseMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(connexion.getHeaderFields());
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        XMLReader xr = null;
        ;
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        this.pp = new SaxReader();
        xr.setContentHandler(pp);
        try {
            xr.parse(new InputSource(in));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //System.out.println("infoip"+pp.info);


        connexion.disconnect();
        setChanged();
        notifyObservers(this.pp);
    }


}
