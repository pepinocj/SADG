package be.kejcs.sadg.Classes;

import java.util.Date;

/**
 * Created by Josi on 13/02/2016.
 */
public class EventBuilder extends ItemBuilder {

    public String label;
    public Date from;
    public Date to;
    public String extraInfo;

    @Override
    public Item createItem() {
        return new Event(super.ID,from,to,label,extraInfo);
    }

    public EventBuilder(ItemCollection ic){
        super(ic);
    }

}
