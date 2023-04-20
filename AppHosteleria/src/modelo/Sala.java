/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThePandogs
 */
public class Sala {

    int id;
    String nombre;

    Local local;
    List<Mesa> mesas;
    List<Mesa> decoraciones;

    public Sala(int id, String nombre, Local local) {
        this.id = id;
        this.nombre = nombre;
        this.local = local;
        mesas = new ArrayList();
        decoraciones = new ArrayList();

    }

    public void anadirMesa(int idMesa, int posicionX, int posicionY, boolean disponible, String imagenURL) {
        mesas.add(new Mesa(idMesa, posicionX, posicionY, disponible, imagenURL));
    }

    public void eliminarMesa(Mesa mesa) {

        mesas.remove(mesa);
    }

    public void cambiarPosicionMesa(Mesa mesa, int x, int y) {

        mesa.cambiarPosicion(x, y);

    }

}
