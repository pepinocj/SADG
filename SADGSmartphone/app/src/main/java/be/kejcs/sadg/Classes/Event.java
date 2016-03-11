package be.kejcs.sadg.Classes;

import java.util.Date;

/**
 * Created by Josi on 13/02/2016.
 */
public class Event  extends Item {
    private Date start;
    private Date end;
    private String label;
    private String extraInfo;
    @Override
    public String toString() {
        return null;
    }

    public Event(long ID,Date start,Date end, String label,String extraInfo){
        super(ID);
        this.start = start;
        this.end = end;
        this.label = label;
        this.extraInfo = extraInfo;
    }


}
