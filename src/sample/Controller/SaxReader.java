package sample.Controller;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import sample.Model.InfoMeteo;

public class SaxReader implements ContentHandler{

    private int afficheValeur;
    private String tag=null;
    public InfoMeteo info=null;

    public SaxReader(){
        this.info = new InfoMeteo();
    }

    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        // TODO Auto-generated method stub
        String donnees = new String(arg0 ,arg1,arg2);
        if(tag.equals("temperature")){
           this.info.setTemperature(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("countryName")){
           // info.setCountryName(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("regionName")){
          //  info.setRegionName(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("cityName")){
           // info.setCityName(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("latitude")){
          //  info.setLatitude(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("longitude")){
           // info.setLongitude(donnees);
            System.out.println( donnees );
        }
        if(tag.equals("timezone")){
           // info.setTimeZone(donnees);
            System.out.println( donnees );
        }


    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        System.out.println("Fin du document");
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
        System.out.println("Debut du document");
    }

    @Override
    public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
        // TODO Auto-generated method stub
        this.tag=arg0+arg1;

        System.out.println( arg0 +arg1+ ":");
    }

    public InfoMeteo getInfo() {
        return info;
    }

    @Override
    public void startPrefixMapping(String arg0, String arg1) throws SAXException {
        // TODO Auto-generated method stub
        //   System.out.println("valeur de arg0 = " +arg0);

    }
}


