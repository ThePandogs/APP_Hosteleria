package modelo;



public class ModelUser {

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ModelUser(int itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;

    }

    public ModelUser() {
    }

    private int itemID;
    private String itemName;

}
