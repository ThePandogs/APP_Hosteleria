/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a14carlosfd
 */
public class Cuenta {

    int idCuenta;
    double precio;
    private Camarero camarero;
    List productos;

    public Cuenta(Camarero camarero) {
        
        precio = 0.0;
        productos = new ArrayList();
        this.camarero = camarero;
    }

    public Cuenta() {
        precio = 0.0;
        productos = new ArrayList();
        this.camarero = null;
    }

    public void anadirProducto(Producto producto, int cantidad) {
    }

    public void eliminarProducto(Producto producto) {
    }

    public void dividirImporte() {
    }

    public void cobrarTarjeta() {
    }

    public void cobrarEfectivo() {
    }

    public void asociarIdTicket() {
    }
}
