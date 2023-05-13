/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a14carlosfd
 */
public class Cuenta {

    private int idCuenta;
    private LocalDateTime fechaHora;
    private Camarero camarero;
    private int comensales;
    private double precio;

    private Map<Producto, Integer> productos;
    private Map<Producto, Integer> pedidoProductos;

    public Cuenta(int idCuenta, LocalDateTime fechaHora, Camarero camarero, int comensales, double precio) {
        this.idCuenta = idCuenta;
        this.fechaHora = fechaHora;
        this.camarero = camarero;
        this.comensales = comensales;
        this.precio = precio;
        this.productos = new HashMap();
        this.pedidoProductos = new HashMap();
    }

    public Cuenta() {

        this.productos = new HashMap();
        this.pedidoProductos = new HashMap();

    }

    public void anadirProductosPedido(Producto producto, int cantidad) {

        if (pedidoProductos.containsKey(producto)) {
            int oldCantidad = pedidoProductos.get(producto);
            int newCantidad = oldCantidad + cantidad;
            pedidoProductos.replace(producto, newCantidad);
        } else {
            pedidoProductos.put(producto, cantidad);
        }

    }

    public void generarPedido() {

        Iterator<Producto> it = pedidoProductos.keySet().iterator();
        while (it.hasNext()) {
            Producto nuevoProducto = it.next();
            int cantidad = pedidoProductos.get(nuevoProducto);
            if (productos.containsKey(nuevoProducto)) {
                añadirProductoExistente(nuevoProducto, cantidad);
            } else {
                productos.put(nuevoProducto, cantidad);
            }
        }
        pedidoProductos.clear();
    }

    private void añadirProductoExistente(Producto producto, int cantidad) {
        int oldCantidad = productos.get(producto);
        int newCantidad = oldCantidad + cantidad;
        productos.replace(producto, newCantidad);
    }

    public void eliminarProducto(Producto producto, int cantidadEliminar) {

        if (pedidoProductos.containsKey(producto)) {
            cantidadEliminar = eliminarProductoPedido(producto, cantidadEliminar);
        }

        if (cantidadEliminar != 0) {
            eliminarProductoCuenta(producto, cantidadEliminar);
        }

    }

    private int eliminarProductoPedido(Producto producto, int cantidadEliminar) {

        int cantidadActual = pedidoProductos.get(producto);

        if (cantidadEliminar >= cantidadActual) {
            pedidoProductos.remove(producto);

        } else {
            pedidoProductos.replace(producto, pedidoProductos.get(producto) - cantidadEliminar);

        }
        return cantidadEliminar - cantidadActual;
    }

    private void eliminarProductoCuenta(Producto producto, int cantidadEliminar) {

        if (productos.get(producto) == cantidadEliminar) {
            productos.remove(producto);

        } else {
            productos.replace(producto, productos.get(producto) - cantidadEliminar);

        }

    }

    public double dividirImporte(Double Precio, int personas) {
        return precio / personas;
    }

    public void cobrarTarjeta() {
    }

    public void cobrarEfectivo() {
    }

    public void asociarIdTicket() {
    }

    public void setData(int idCuenta, LocalDateTime fechaHora, Camarero camarero, int comensales, double precio, List productos) {

        this.idCuenta = idCuenta;
        this.fechaHora = fechaHora;
        this.camarero = camarero;
        this.comensales = comensales;
        this.precio = precio;
        this.productos = new HashMap();

    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setProductos(Map productos) {
        this.productos = productos;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Camarero getCamarero() {
        return camarero;
    }

    public int getComensales() {
        return comensales;
    }

    public double getPrecio() {
        return precio;
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public Map<Producto, Integer> getPedidoProductos() {
        return pedidoProductos;
    }

}
