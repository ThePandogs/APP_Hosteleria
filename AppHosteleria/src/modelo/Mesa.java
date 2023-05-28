package modelo;

/**
 * Clase que representa una mesa en un local.
 */
public class Mesa {

    private int idMesa;
    private int numero;
    private Cuenta cuenta;
    private String imagenURL;
    private boolean disponible;
    private int x;
    private int y;
    private int width;
    private int height;

      /**
     * Crea una instancia de Mesa con los valores proporcionados.
     *
     * @param idMesa el ID de la mesa
     * @param numero el número de la mesa
     * @param posicionX la posición X de la mesa
     * @param posicionY la posición Y de la mesa
     * @param width el ancho de la mesa
     * @param height la altura de la mesa
     * @param disponible indica si la mesa está disponible o no
     */
    public Mesa(int idMesa, int numero, int posicionX, int posicionY, int width, int height, boolean disponible) {
        this.idMesa = idMesa;
        this.numero = numero;
        this.x = posicionX;
        this.y = posicionY;
        this.width = width;
        this.height = height;
        this.disponible = disponible;

    }

// <editor-fold defaultstate="collapsed" desc="Getter_Setter">
    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumero() {
        return numero;
    }

// </editor-fold>
}
