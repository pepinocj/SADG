package be.jhoobergs.mathgame.Classes;

/**
 * Created by Josi on 13/02/2016.
 */
public abstract class Item {
    public long ID;

    public Item(long ID){
        this.ID = ID;
    }

    public abstract String toString();
}
