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

    String nombre;
    List<Mesa> mesas;
    Local local;

    public Sala(String nombre) {
        this.nombre = nombre;
        mesas = new ArrayList();
        this.local = local;
    }

    public void anadirMesa(Camarero camarero, int idmesa) {

        mesas.add(new Mesa(camarero, idmesa));
    }

    public void eliminarMesa(Mesa mesa) {

        mesas.remove(mesa);
    }

}
