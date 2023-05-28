/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ControllerBBDD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a14carlosfd
 */
/**
 * Clase que implementa la interfaz GestionPedidos y se encarga de gestionar los
 * pedidos, cuentas y usuarios del sistema.
 */
public class Gestion implements GestionPedidos {

    ControllerBBDD controllerBBDD = new ControllerBBDD();
    Camarero camareroActual;
    ArrayList<Local> locales;
    ArrayList<Usuario> usuarios;
    Usuario usuarioActual;

    /**
     * Constructor de la clase Gestion. Inicializa las listas de locales y
     * usuarios, y carga los datos desde la base de datos.
     */
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
                locales.get(0).productos.put(consulta.getInt("id_producto"), nuevoProducto);

            }
            return true;
        }
        return false;
    }

    private void cargarTodo() throws SQLException {

        cargarLocales();
        if (!locales.isEmpty()) {
            Iterator<Local> localesIte = locales.iterator();
            while (localesIte.hasNext()) {
                Local local = localesIte.next();
                cargarCamareros();

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
            usuarios.add(new Usuario(consulta.getInt("id_usuario"), consulta.getInt("empleado"), consulta.getString("usuario"), consulta.getString("rol")));
        }

    }

    private boolean cargarCamareros() throws SQLException {

        ResultSet consulta = controllerBBDD.consultarCamareros();

        if (consulta != null) {
            while (consulta.next()) {
                locales.get(0).camareros.put(consulta.getInt("id_empleado"), new Camarero(consulta.getInt("id_empleado"), consulta.getString("nombre")));
            }
            return true;
        }
        return false;
    }

    private boolean cargarCuenta(Mesa mesa) throws SQLException {

        int id_mesa = mesa.getIdMesa();
        ResultSet consulta = controllerBBDD.consultarCuenta(id_mesa);
        if (consulta != null) {
            while (consulta.next()) {
                Cuenta cuentaNueva = new Cuenta(consulta.getInt("id_cuenta"),
                        consulta.getTimestamp("fecha_hora").toLocalDateTime(),
                        locales.get(0).camareros.get(consulta.getInt("camarero")),
                        consulta.getInt("comensales"),
                        mesa
                );
                mesa.setCuenta(cuentaNueva);
                cargarPedidos(cuentaNueva);
            }
            return true;
        }
        return false;

    }

    /**
     * Carga los pedidos de una cuenta específica. Recibe como parámetro una
     * instancia de la clase Cuenta. Realiza una consulta en la base de datos
     * para obtener los pedidos asociados a la cuenta. Crea un mapa de productos
     * y cantidades a partir de los resultados de la consulta. Actualiza los
     * productos de la cuenta con el mapa obtenido.
     *
     * @param cuenta la cuenta de la cual se cargarán los pedidos
     * @throws SQLException si ocurre un error al realizar la consulta en la
     * base de datos
     */
    @Override
    public void cargarPedidos(Cuenta cuenta) throws SQLException {

        int id_cuenta = cuenta.getIdCuenta();
        ResultSet consulta = controllerBBDD.consultarPedido(id_cuenta);
        Map<Producto, Integer> productos = new HashMap();
        if (consulta != null) {
            while (consulta.next()) {

                productos.put(locales.get(0).getProductos().get(consulta.getInt("producto")), consulta.getInt("cantidad"));

            }
        }
        cuenta.setProductos(productos);
    }

    /*carga las mesas, si consulta es null retorna false*/
    private boolean cargarMesas(Sala sala) throws SQLException {
        int id_sala = sala.getId();
        ResultSet consulta = controllerBBDD.consultarMesasPorSala(id_sala);
        if (consulta != null) {
            while (consulta.next()) {
                Mesa nuevaMesa = new Mesa(consulta.getInt("id_mesa"),
                        consulta.getInt("numero"),
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

    /**
     * Comprueba si un usuario y contraseña son válidos.
     *
     * @param usuario el nombre de usuario a comprobar
     * @param password la contraseña a comprobar
     * @return true si el usuario y contraseña son válidos, false en caso
     * contrario
     */
    public boolean comprobarUsuario(String usuario, String password) {
        return controllerBBDD.comprobarPasswordUsuario(usuario, password);
    }

    /**
     * Inserta una nueva cuenta en la base de datos.
     *
     * @param cuenta la cuenta a insertar
     */
    public void insertarCuentaNueva(Cuenta cuenta) {
        try {
            ResultSet id_cuenta = controllerBBDD.insertarNuevaCuenta(camareroActual.getId(), cuenta.getMesa().getIdMesa(), cuenta.getComensales());
            while (id_cuenta.next()) {
                cuenta.setIdCuenta(id_cuenta.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la cuenta asociada a una mesa y la marca como no asignada.
     *
     * @param mesa a la que se le cerrará la cuenta.
     */
    public void cerrarMesa(Mesa mesa) {
        controllerBBDD.cerrarCuenta(mesa.getCuenta().getMetodoPago(), mesa.getCuenta().getIdCuenta());
        mesa.setCuenta(null);
    }

    /**
     * Envía un pedido asociado a una cuenta, registrando los nuevos productos y
     * sus cantidades.
     *
     * @param cuenta La cuenta a la que se le enviará el pedido.
     */
    @Override
    public void enviarPedido(Cuenta cuenta) {
        Map<Producto, Integer> nuevosProductos = cuenta.getPedidoProductos();
        Iterator<Producto> itNuevosProductos = nuevosProductos.keySet().iterator();

        while (itNuevosProductos.hasNext()) {
            Producto producto = itNuevosProductos.next();
            int cantidad = nuevosProductos.get(producto);
            if (cuenta.getIdCuenta() == 0) {
                insertarCuentaNueva(cuenta);
            }
            controllerBBDD.enviarPedido(producto.getId(), cuenta.getIdCuenta(), cantidad);
        }
        cuenta.generarPedido();
    }

    /**
     * Borra un producto de una cuenta, actualizando las cantidades
     * correspondientes.
     *
     * @param cuenta La cuenta de la cual se eliminará el producto.
     * @param producto El producto a eliminar.
     * @param cantidad La cantidad del producto a eliminar.
     * @return          <code>true</code> si el producto se eliminó exitosamente,
     * <code>false</code> si no.
     */
    public boolean borrarProducto(Cuenta cuenta, Producto producto, int cantidad) {
        int cantidadProductos = cuenta.getProductos().getOrDefault(producto, 0);
        int cantidadPedidos = cuenta.getPedidoProductos().getOrDefault(producto, 0);

        int suma = cantidadProductos + cantidadPedidos;
        if (cantidad <= suma) {
            controllerBBDD.eliminarProducto(producto.getId(), cuenta.getIdCuenta(), cantidad);
            cuenta.eliminarProducto(producto, cantidad);
            return true;
        }
        return false;
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

    public Camarero getCamareroActual() {
        return camareroActual;
    }

    public void setCamareroActual(Camarero camareroActual) {
        this.camareroActual = camareroActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
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
