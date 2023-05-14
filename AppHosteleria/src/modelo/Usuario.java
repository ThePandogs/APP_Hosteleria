package modelo;

public class Usuario {

    private int idUsuario;
    private int idEmpleado;
    private String itemName;
    private String rol;

    public Usuario(int itemID, int idEmpleado, String itemName, String rol) {
        this.idUsuario = itemID;
        this.idEmpleado = idEmpleado;
        this.itemName = itemName;
        this.rol = rol;

    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
