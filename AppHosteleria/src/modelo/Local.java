/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ControllerBBDD;
import iu.Interfaz;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThePandogs
 */
public class Local {

    String nombre;
    String cif;
    String direccion;

    List<Sala> salas;
    List<Camarero> camareros;
    List<GrupoProducto> gruposProductos;

    public Local(String nombre, String cif, String direccion) {

        salas = new ArrayList();
        camareros = new ArrayList();
        gruposProductos = new ArrayList();

    }

    public void anadirSala(Sala sala) {
        salas.add(sala);
    }

    public void borrarSala(Sala sala) {
        salas.remove(sala);
    }

    public void anadirGrupoProducto(GrupoProducto grupo) {
        gruposProductos.add(grupo);
    }

    public void cierreCaja() {
    }

}
