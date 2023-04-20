/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a14carlosfd
 */
public class Cuenta {

    private int idCuenta;
    private double precio;
    private String factura;
    private LocalDateTime fechaHora;

    private Local local;
    private Mesa mesa;
    private Camarero camarero;
    private List productos;
    private List nuevosProductos;

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

    public void anadirNuevosProductos(Producto producto, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            nuevosProductos.add(producto);
        }
    }

    public void pedirNuevosProducto() {
        productos.addAll(nuevosProductos);
        generarPedido(nuevosProductos);
        nuevosProductos.clear();

    }

    public void eliminarProducto(Producto producto) {

        productos.remove(producto);

    }

    private void generarPedido(List<Producto> nuevosProductos) {
        //cargar productos a base de datos para hacer el trigger de pedido.
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
