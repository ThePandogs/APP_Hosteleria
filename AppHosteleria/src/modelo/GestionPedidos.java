/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import java.sql.SQLException;

/**
 *
 * @author thepandogs
 */
public interface GestionPedidos {

    /**
     * Envía un pedido asociado a una cuenta, registrando los nuevos productos y
     * sus cantidades.
     *
     * @param cuenta La cuenta a la que se le enviará el pedido.
     */
    public void enviarPedido(Cuenta cuenta);

    /**
     * Carga los pedidos de una cuenta desde la base de datos.
     *
     * @param cuenta La cuenta a la que se cargarán los pedidos.
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos.
     */
    public void cargarPedidos(Cuenta cuenta) throws SQLException;
}
