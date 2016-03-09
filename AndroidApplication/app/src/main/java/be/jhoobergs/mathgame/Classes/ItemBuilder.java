package be.jhoobergs.mathgame.Classes;

/**
 * Created by Josi on 13/02/2016.
 */
public abstract class ItemBuilder {
    protected long ID;
    private ItemCollection ic;

    public ItemBuilder(ItemCollection ic){
        this.ID = System.currentTimeMillis();
        this.ic = ic;
    }

    public abstract Item createItem();
    public  void reset(){

    };
    public void finalizeItem(){
        ic.addItem(createItem());
    }
}
