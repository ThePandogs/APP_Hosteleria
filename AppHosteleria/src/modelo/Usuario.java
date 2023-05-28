package modelo;

/**
 * Representa a un usuario con su información asociada.
 */
public class Usuario {

    private int idUsuario;
    private int idEmpleado;
    private String itemName;
    private String rol;

    /**
     * Crea una nueva instancia de la clase Usuario con los valores
     * proporcionados.
     *
     * @param itemID el ID del usuario
     * @param idEmpleado el ID del empleado asociado al usuario
     * @param itemName el nombre del usuario
     * @param rol el rol del usuario
     */
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
