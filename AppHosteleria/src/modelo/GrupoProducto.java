/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author thepandogs

 * Clase que representa un grupo de productos.
 */
public class GrupoProducto {

    private int id;
    private String nombre;
    private ArrayList<Producto> productos;

    /**
     * Crea una instancia de GrupoProducto con los valores proporcionados.
     *
     * @param idGrupoProducto el ID del grupo de productos
     * @param nombre el nombre del grupo de productos
     */
    public GrupoProducto(int idGrupoProducto, String nombre) {
        this.id = idGrupoProducto;
        this.nombre = nombre;
        productos = new ArrayList();
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

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

}
