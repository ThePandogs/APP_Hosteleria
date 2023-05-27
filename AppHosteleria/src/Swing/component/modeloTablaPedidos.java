/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swing.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

/**
 *
 * @author fraid
 */
public class ModeloTablaPedidos extends DefaultTableModel {

 private Map<Producto, Integer> listaProductos = new HashMap();
    Map<Producto, Integer> productosPedido;

    public ModeloTablaPedidos() {

        addColumn("Producto");
        addColumn("Precio");
        addColumn("Cant.");

    }

    @Override
    public int getColumnCount() {

        return 3; // 3 columnas: nombre, precio y cantidad
    }

    @Override
    public int getRowCount() {
        if (listaProductos == null) {
            listaProductos = new HashMap();
        }
        return listaProductos.size(); // Número de filas según la cantidad de productos
    }

    public void setProductos(Map<Producto, Integer> productosPedido) {

        this.productosPedido = productosPedido;
        actualizarProductos();
    }
    public void vaciarTabla() {
        listaProductos.clear();
        fireTableDataChanged();
    }


    public void actualizarProductos() {
      listaProductos.clear();

        listaProductos.putAll(productosPedido);
        fireTableDataChanged();
    }

    public void agregarProducto(Producto producto, int cantidad) {
 
        int rowIndex = productosPedido.size() - 1;

        fireTableRowsInserted(rowIndex, rowIndex);
    }



    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        List<Producto> a = new ArrayList<>(productosPedido.keySet());
        Producto producto = a.get(row);
        return switch (column) {
            case 0 ->
                producto.getNombre();
            case 1 ->
                producto.getPrecio();
            case 2 ->
                productosPedido.get(producto);
            case 3 ->
                producto;
            default ->
                null;
        };

    }

}
