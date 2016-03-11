package be.kejcs.sadg.Classes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josi on 13/02/2016.
 */
public class ItemCollection {
    public static String path = "foobar";
    private List<Item> items;
    private File json;

    public ItemCollection(){
        this.items = new ArrayList<Item>();

    }

    public void generateFile(){

    }

    public void addItem(Item i){
        items.add(i);
    }

    public void removeItem(Item i){
        items.remove(i);
    }

    public List<Item> getItems(){
        return this.items;
    }

    public void sync(){

    }



}
