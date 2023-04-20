/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ConexionBBDD;
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
    List<Camarero> Camareros;
    List<GrupoProducto> GruposProductos;

    ConexionBBDD conexionBBDD;
    Interfaz interfaz;

    public Local(Interfaz interfaz) {
        this.salas = new ArrayList();
        this.Camareros = new ArrayList();
        this.interfaz = interfaz;
        conexionBBDD = new ConexionBBDD();
    }

    public void anadirSala(String nombre, int ancho, int alto) {

        conexionBBDD.modificacion(conexionBBDD.getQueryAnadirSala());

    }

    public void borrar(Sala sala) {
        salas.remove(sala);
    }

    public void cargarProductos() {

    }

    public void cierreCaja() {
    }

}
