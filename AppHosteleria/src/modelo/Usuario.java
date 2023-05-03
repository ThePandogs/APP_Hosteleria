package modelo;

public class Usuario {

    private int itemID;
    private String itemName;
    private String rol;

    public Usuario(int itemID, String itemName, String rol) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.rol = rol;

    }

    public Usuario() {
    }

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
}
