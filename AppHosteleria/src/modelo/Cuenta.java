/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
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
    private double totalCuenta;
    private Mesa mesa;
    private String metodoPago;
    private Map<Producto, Integer> productos;
    private Map<Producto, Integer> pedidoProductos;

    public Cuenta(int idCuenta, LocalDateTime fechaHora, Camarero camarero, int comensales, Mesa mesa) {
        this.idCuenta = idCuenta;
        this.fechaHora = fechaHora;
        this.camarero = camarero;
        this.comensales = comensales;
        this.mesa = mesa;
        this.productos = new HashMap();
        this.pedidoProductos = new HashMap();

    }

    public Cuenta(Mesa mesa, Camarero camarero) {
        this.mesa = mesa;
        this.camarero = camarero;
        this.productos = new HashMap();
        this.pedidoProductos = new HashMap();
        totalCuenta = 0.0;
    }

    public void actualizarTotalCuenta() {
        totalCuenta = 0.0;
        if (!productos.isEmpty()) {
            for (Map.Entry<Producto, Integer> entry1 : productos.entrySet()) {
                double precio = entry1.getKey().getPrecio();
                int cantidad = entry1.getValue();
                totalCuenta += (cantidad * precio);
            }
        }
        if (!pedidoProductos.isEmpty()) {
            for (Map.Entry<Producto, Integer> entry2 : pedidoProductos.entrySet()) {
                double precio = entry2.getKey().getPrecio();
                int cantidad = entry2.getValue();
                totalCuenta += (cantidad * precio);
            }
        }
    }

    public void anadirProductosPedido(Producto producto, int cantidad) {

        if (pedidoProductos.containsKey(producto)) {
            int oldCantidad = pedidoProductos.get(producto);
            int newCantidad = oldCantidad + cantidad;
            pedidoProductos.replace(producto, newCantidad);
        } else {
            pedidoProductos.put(producto, cantidad);
        }
        actualizarTotalCuenta();
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
        actualizarTotalCuenta();
    }

    public void eliminarProducto(Producto producto, int cantidadEliminar) {

        if (pedidoProductos.containsKey(producto)) {
            cantidadEliminar = eliminarProductoPedido(producto, cantidadEliminar);
        }

        if (cantidadEliminar != 0) {
            eliminarProductoCuenta(producto, cantidadEliminar);
        }
        actualizarTotalCuenta();
    }

    private int eliminarProductoPedido(Producto producto, int cantidadEliminar) {

        int cantidadActual = pedidoProductos.get(producto);

        if (cantidadEliminar >= cantidadActual) {
            pedidoProductos.remove(producto);

        } else {
            pedidoProductos.replace(producto, pedidoProductos.get(producto) - cantidadEliminar);
            return 0;

        }

        return cantidadActual - cantidadEliminar;

    }

    private void eliminarProductoCuenta(Producto producto, int cantidadEliminar) {

        if (productos.get(producto) == cantidadEliminar) {
            productos.remove(producto);

        } else {
            productos.replace(producto, productos.get(producto) - cantidadEliminar);

        }
        actualizarTotalCuenta();
    }

    public void setData(int idCuenta, LocalDateTime fechaHora, Camarero camarero, int comensales, HashMap productos) {

        this.idCuenta = idCuenta;
        this.fechaHora = fechaHora;
        this.camarero = camarero;
        this.comensales = comensales;
        this.productos = productos;
        actualizarTotalCuenta();
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setProductos(Map productos) {
        this.productos = productos;
        actualizarTotalCuenta();
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

    public double getTotalCuenta() {
        return totalCuenta;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public Map<Producto, Integer> getPedidoProductos() {
        return pedidoProductos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public boolean comprobarCierreMesa() {
        return pedidoProductos.isEmpty();
    }

}
