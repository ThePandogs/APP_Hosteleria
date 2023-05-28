/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ThePandogs
 *
 * Clase que representa a un camarero.
 */
public class Camarero {

    private int id;
    private String nombre;

    /**
     * Crea una instancia de Camarero.
     *
     * @param id el ID del camarero
     * @param nombre el nombre del camarero
     */
    public Camarero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

}
