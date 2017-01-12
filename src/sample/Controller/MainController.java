package sample.Controller;
import sample.Model.Day;
import sample.Model.InfoMeteo;
import sample.Model.Result;
import sample.Model.SaxReader;
import sample.jfx.MeteoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;



/**
 * Created by axel on 11/01/2017.
 */
public class MainController {


        /**
         * ==========================================================================================
         *  FIELDS
         * ==========================================================================================
         */

        private Day model = null;
        private Result search = null;





        /**
         * ==========================================================================================
         *  CONSTRUCTORS
         * ==========================================================================================
         */

        /**
         * Default constructeur
         */
        public MainController (MeteoView jfxView){
            this.model= new Day();
            System.out.println("init Day");

            model.addObserver(jfxView);
            this.search=new Result();
            search.addObserver(jfxView);


        }


        public void setIter(ListIterator<InfoMeteo> iter){
            this.model.setIter(iter);
        }


        /**
         * ==========================================================================================
         *  PUBLIC METHODS
         * ==========================================================================================
         */


        public void notifyChangeJour(int bool){
            //System.out.println("lollllolol");
            model.setCourant(bool);
        }
        public void notifySearch(String ville){
             search.parse(ville);

         }



    public Day getModel() {
        return model;
    }

    public void setModel(Day model) {
        this.model = model;
    }
}

