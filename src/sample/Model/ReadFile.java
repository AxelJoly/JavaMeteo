package sample.Model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by maxim on 22/12/2016.
 */

public class ReadFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFile.class);

    /**
     *  Charge le fichier texte dans notre ArrayList
     */

    public void loadBookmarks(ArrayList<String> bookmarks, String file) {
        String chaine = "";
        int compt;
        int i =0;
        String nom = null;


        //lecture du fichier texte
        try {
            InputStream ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            compt = 0;

            while ((ligne = br.readLine()) != null) {
                if (compt == 0) {

                    nom = ligne;
                   // bookmarks.getItems().add(bookmarks.getItems().size(), nom);
                   // LOGGER.info(bookmarks.getItems().get(1));
                    bookmarks.add(i, ligne);
                    i++;
                }


                LOGGER.info(ligne);
                chaine += ligne + "\n";

            }
            br.close();
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }


    }

    /**
     *  Crée une copie du fichier .txt actuel, le modifie puis l'efface pour actualiser l'effacement d'une ville
     */

    public void deleteItem(String value, String file) {


        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(value)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }





}
