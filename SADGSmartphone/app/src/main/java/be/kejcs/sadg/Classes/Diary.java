package be.kejcs.sadg.Classes;

import android.util.Pair;

import java.util.Date;
import java.util.List;

/**
 * Created by Josi on 13/02/2016.
 */
public class Diary  extends Item {
    private List<Pair<String,String>> entries;
    private Date date;
    @Override
    public String toString() {
        return null;
    }



    public Diary(long ID,List<Pair<String,String>> entries,Date date){
        super(ID);
        this.entries = entries;
        this.date = date;
    }


}
