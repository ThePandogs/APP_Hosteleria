package modelo;

public class Mesa {

    private int idMesa;
    private Cuenta cuenta;
    private String imagenURL;
    private boolean disponible;
    private int x;
    private int y;
    private int width;
    private int height;

    public Mesa(int idMesa, int posicionX, int posicionY, int width, int height, boolean disponible) {
        this.idMesa = idMesa;
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
// </editor-fold>

}
