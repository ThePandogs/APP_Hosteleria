/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ControllerBBDD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a14carlosfd
 */
public class Gestion {

    ControllerBBDD controllerBBDD = new ControllerBBDD();

    ArrayList<Local> locales;
    ArrayList<Usuario> usuarios;

    public Gestion() {
        locales = new ArrayList();
        usuarios = new ArrayList();
        try {

            cargarUsuarios();
            cargarTodo();

        } catch (SQLException ex) {
            Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarLocales() throws SQLException {

        ResultSet consulta = controllerBBDD.consultarEstablecimientos();
        while (consulta.next()) {

            locales.add(new Local(consulta.getInt("id_establecimiento"),
                    consulta.getString("nombre"),
                    consulta.getString("direccion"),
                    consulta.getString("cif"),
                    consulta.getString("prefijo_telefono"),
                    consulta.getString("telefono")));
        }

    }

    private void cargarGruposProductos(Local local) throws SQLException {

        ResultSet consulta = controllerBBDD.consultarGruposProductos();

        while (consulta.next()) {
            GrupoProducto nuevoGrupo = new GrupoProducto(consulta.getInt("id_grupo"),
                    consulta.getString("nombre"));
            local.getGruposProductos().add(nuevoGrupo);
            cargarProductosGrupo(nuevoGrupo);
        }

    }

    /*carga los productos, si consulta es null retorna false*/
    private boolean cargarProductosGrupo(GrupoProducto grupoProducto) throws SQLException {
        int id_grupo = grupoProducto.getId();
        ResultSet consulta = controllerBBDD.consultarProductosPorGrupo(id_grupo);

        if (consulta != null) {
            while (consulta.next()) {
                Producto nuevoProducto = new Producto(consulta.getInt("id_producto"),
                        consulta.getString("nombre"),
                        consulta.getDouble("precio"),
                        consulta.getBoolean("disponible"),
                        consulta.getString("imagen"));
                grupoProducto.getProductos().add(nuevoProducto);

            }
            return true;
        }
        return false;
    }

    private void cargarProductos() throws SQLException {

        ResultSet consulta = controllerBBDD.consultarProductos();
        while (consulta.next()) {
            int id_producto = consulta.getInt("id_producto");
            locales.get(0).getProductos().put(id_producto, new Producto(id_producto,
                    consulta.getString("nombre"),
                    consulta.getDouble("precio"),
                    consulta.getBoolean("disponible"),
                    consulta.getString("imagen")));

        }
    }

    private void cargarTodo() throws SQLException {

        cargarLocales();
        if (!locales.isEmpty()) {
            Iterator<Local> localesIte = locales.iterator();
            while (localesIte.hasNext()) {
                Local local = localesIte.next();
                cargarCamareros();
                 cargarProductos();
                cargarGruposProductos(local);
                cargarSalas(local);
               

            }
        }

    }

    private void cargarSalas(Local local) throws SQLException {

        ResultSet consulta = controllerBBDD.consultarSalas();

        while (consulta.next()) {
            Sala nuevaSala = new Sala(consulta.getInt("id_sala"),
                    consulta.getString("nombre"));
            local.getSalas().add(nuevaSala);
            cargarMesas(nuevaSala);
        }

    }

    private void cargarUsuarios() throws SQLException {
        ResultSet consulta = controllerBBDD.cargarUsuarios();
        while (consulta.next()) {
            usuarios.add(new Usuario(consulta.getInt("id_usuario"), consulta.getString("usuario"), consulta.getString("rol")));
        }

    }

    private boolean cargarCamareros() throws SQLException {

        ResultSet consulta = controllerBBDD.consultarCamareros();
        locales.get(0).camareros.add(null);
        if (consulta != null) {
            while (consulta.next()) {
                locales.get(0).camareros.add(new Camarero(consulta.getInt("id_empleado"), consulta.getString("nombre")));
            }
            return true;
        }
        return false;
    }

    private void cargarCamareroUsuario(int id_usuario) {

        ResultSet consulta = controllerBBDD.consultarEmpleadoUsuario(id_usuario);

    }

    public boolean cargarCuenta(Mesa mesa) throws SQLException {

        int id_mesa = mesa.getIdMesa();
        ResultSet consulta = controllerBBDD.consultarCuenta(id_mesa);
        if (consulta != null) {
            while (consulta.next()) {
                Cuenta cuentaNueva = new Cuenta(consulta.getInt("id_cuenta"),
                        consulta.getTimestamp("fecha_hora").toLocalDateTime(),
                        locales.get(0).camareros.get(consulta.getInt("camarero")),
                        consulta.getInt("comensales"),
                        consulta.getDouble("precio")
                );
                mesa.setCuenta(cuentaNueva);
                cargarPedidos(cuentaNueva);
            }
            return true;
        }
        return false;

    }

    public void cargarPedidos(Cuenta cuenta) throws SQLException {

        int id_cuenta = cuenta.getIdCuenta();
        ResultSet consulta = controllerBBDD.consultarPedido(id_cuenta);
        Map<Producto, Integer> productos = cuenta.getProductos();
        if (consulta != null) {
            while (consulta.next()) {
                System.out.println(locales.get(0).getProductos().get(consulta.getInt("producto")));
                productos.put(locales.get(0).getProductos().get(consulta.getInt("producto")), consulta.getInt("cantidad"));

            }

        }

    }

    /*carga las mesas, si consulta es null retorna false*/
    private boolean cargarMesas(Sala sala) throws SQLException {
        int id_sala = sala.getId();
        ResultSet consulta = controllerBBDD.consultarMesasPorSala(id_sala);
        if (consulta != null) {
            while (consulta.next()) {
                Mesa nuevaMesa = new Mesa(consulta.getInt("id_mesa"),
                        consulta.getInt("posicionX"),
                        consulta.getInt("posicionY"),
                        consulta.getInt("tamanoX"),
                        consulta.getInt("tamanoY"),
                        consulta.getBoolean("disponible"));
                sala.getMesas().add(nuevaMesa);
                cargarCuenta(nuevaMesa);
            }

            return true;
        }
        return false;
    }

    public boolean comprobarUsuario(String usuario, String password) {
        return controllerBBDD.comprobarPasswordUsuario(usuario, password);
    }

    public ArrayList<Local> getLocales() {
        return locales;
    }

    public void setLocales(ArrayList<Local> locales) {
        this.locales = locales;
    }

    public ControllerBBDD getControllerBBDD() {
        return controllerBBDD;
    }

    public void setControllerBBDD(ControllerBBDD controllerBBDD) {
        this.controllerBBDD = controllerBBDD;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
