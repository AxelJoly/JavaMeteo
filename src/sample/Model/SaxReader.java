package sample.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaxReader extends Observable implements ContentHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(SaxReader.class);
    protected int afficheValeur;
    protected String tag=null;
    protected InfoMeteo info=null;
    protected long heureActu=0;
    protected Calendar calendar = new GregorianCalendar();
    public ArrayList<InfoMeteo> tabDay = new ArrayList<InfoMeteo>();
    protected String name;
    protected String country;
    protected int boolMorning;
    protected int boolAf;

    public SaxReader(){
        // Initialisation du calendrier et des variables pour le matin et l'aprem
        boolMorning=0;
        boolAf =0;
        calendar.setTime(new Date(0));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



    }

    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        // TODO Auto-generated method stub
        String donnees = new String(arg0, arg1, arg2);

        //Récupère le nom
        if (tag.equals("name")) {

            this.name=donnees;

        }
        //Récupère la ville
        if (tag.equals("country")) {

            this.country=donnees;

        }

    }
    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        LOGGER.info("Document end");
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2) throws SAXException {
        // TODO Auto-generated method stub
        //   System.out.println("valeur de arg0 = " +arg0);
    }

    @Override
    public void endPrefixMapping(String arg0) throws SAXException {
        // TODO Auto-generated method stub
        //  System.out.println("valeur de arg0 = " +arg0);
    }

    @Override
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
        // TODO Auto-generated method stub
        //  System.out.println("valeur de arg0 = " +arg0);
    }

    @Override
    public void processingInstruction(String arg0, String arg1) throws SAXException {
        // TODO Auto-generated method stub
        //   System.out.println("valeur de arg0 = " +arg0);
    }

    @Override
    public void setDocumentLocator(Locator arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void skippedEntity(String arg0) throws SAXException {
        // TODO Auto-generated method stub
        // System.out.println("valeur de arg0 = " +arg0);

    }

    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        LOGGER.info("Document start");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {


        this.tag = uri + localName;
        if (tag.equals("time")) {

            //Initialisation du format de la date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dateInString = attributes.getValue(1);

            try {
                //On recupère la date actuelle
                Date date = formatter.parse(dateInString);

                //Initialisation d'un nouveau calendrier
                Calendar calStart = new GregorianCalendar();
                calStart.setTime(date);
                calStart.set(Calendar.HOUR_OF_DAY, 12);
                calStart.set(Calendar.MINUTE, 0);
                calStart.set(Calendar.SECOND, 0);
                calStart.set(Calendar.MILLISECOND, 0);

                if ((date.compareTo(calendar.getTime()) == 1)) {

                    boolAf =0;
                    boolMorning=0;

                   // Ajout d'un objet InfoMeteo a l'ArrayList
                    this.info=new InfoMeteo();

                    //On insère les données dans notre objet
                    tabDay.add(info);
                    info.setCity(this.name);
                    info.setCountry(this.country);
                    info.setDate(date);


                    //On commence la comparaison de la date actuelle avec celle initialisé dans le constructeur
                    if (date.compareTo(calStart.getTime()) == 1) {

                        //On est l'aprem: on incrémente
                        boolAf++;

                    } else {

                        //On est dans le matin: on incrémente
                        boolMorning++;
                    }
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                } else {
                    if (date.compareTo(calStart.getTime()) == 1) {

                        boolAf++;

                    } else {

                        boolMorning++;
                    }

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        if (tag.equals("temperature")) {

            // On recupère la temperature du matin et de l'aprem
            if(boolMorning==3) {

                info.setMorningTemperature(attributes.getValue(1));

                boolMorning++;

            }else if(boolAf ==1){

                info.setAfTemperature(attributes.getValue(1));


                if(info.getMorningTemperature()==null){

                    info.setMorningTemperature(attributes.getValue(1));
                }

                boolAf++;
            }
        }

        //On recupère le temps
        if (tag.equals("symbol")) {

            if(boolMorning==1) {
                info.setMorningImage(attributes.getValue(1));


            }else if(boolAf ==1){
                info.setAfImage(attributes.getValue(1));

                if(info.getMorningImage()==null){
                   // System.out.println("quel temps fait il? " + attributes.getValue(1));

                    info.setMorningImage(attributes.getValue(1));
                }

            }

        }
    }


    public InfoMeteo getInfo() {
        return info;
    }

    public void setInfo(InfoMeteo info){
        this.info = info;
        setChanged();
        notifyObservers(this.info);
    }

    @Override
    public void startPrefixMapping(String arg0, String arg1) throws SAXException {
        // TODO Auto-generated method stub


    }


}


