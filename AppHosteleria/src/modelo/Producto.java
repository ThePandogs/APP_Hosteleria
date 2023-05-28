/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author thepandogs
 *
 * La clase Producto representa un producto en el sistema de gestión de pedidos.
 * Contiene información como el identificador, nombre, precio, disponibilidad y
 * ruta de la imagen del producto.
 */
public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private boolean disponible;
    private String imagen;

    /**
     * Constructor de la clase Producto.
     *
     * @param id el identificador del producto
     * @param nombre el nombre del producto
     * @param precio el precio del producto
     * @param disponible indica si el producto está disponible
     * @param imagen la ruta de la imagen del producto
     */
    public Producto(int id, String nombre, double precio, boolean disponible, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
