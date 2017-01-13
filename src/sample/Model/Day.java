package sample.Model;

import sample.jfx.MeteoView;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;

/**
 * Created by axel on 11/01/2017.
 */
public class Day extends Observable {



    public ListIterator<InfoMeteo> array;
    public InfoMeteo info;

    public Day(){


    }
    /**
     *  Appelle le prochain element de notre listIterator et le met dans notre objet courant
     */

    public void setIter(ListIterator<InfoMeteo> iter){
        this.array=iter;
        info = iter.next();
        setChanged();
        notifyObservers(this.info);
    }

    /**
     *  Methode appellé dans next() et last() pour changer la valeur de notre objet au jour suivant ou precedent
     */

    public void setCourant(int current){

        if(current==1){
            if (array.hasPrevious()) {
                info = array.previous();
            }
        }
        if(current==0){
            if (array.hasNext()) {
                info = array.next();
            }
        }
        setChanged();
        notifyObservers(this.info);

    }

    public InfoMeteo getInfo() {
        return info;
    }

    public void setInfo(InfoMeteo info) {
        this.info = info;
    }
}
