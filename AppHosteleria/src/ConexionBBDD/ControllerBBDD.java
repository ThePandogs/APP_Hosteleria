/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBBDD;

import Log.LogExcepcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author thepandogs
 *
 * Esta clase representa un controlador para gestionar operaciones de base de
 * datos.
 */
public class ControllerBBDD {

    private LogExcepcion logExcepcion = new LogExcepcion();

    //QUERYS
    private String queryGetUsuarios = "select id_usuario,empleado,usuario,rol from usuarios;";
    private String queryConsultarProductos = "select id_producto,nombre,precio,disponible,imagen from productos;";
    private String queryComprobarPasswordUsuario = "SELECT * FROM usuarios WHERE usuario = ? AND pin = ?;";
    private String queryConsultarMesas = "select id_mesa,numero,posicionX,posicionY,tamanoX,tamanoY,disponible from mesas;";
    private String queryConsultarMesasPorSala = "select id_mesa,numero,posicionX,posicionY,tamanoX,tamanoY,disponible from mesas where sala=?;";
    private String queryConsultarProductosPorGrupo = "select id_producto,nombre,precio,disponible,imagen from productos where grupo=?;";
    private String queryConsultarSalas = "select id_sala,nombre from salas;";
    private String queryConsultarEstablecimientos = "select id_establecimiento,nombre,direccion,cif,prefijo_telefono,telefono from establecimientos;";
    private String queryConsultarGruposProductos = "select id_grupo,nombre from grupos_productos;";
    private String queryConsultarPedido = "select producto,cantidad from pedidos where cuenta=?";
    private String queryInsertarNuevaCuenta = "INSERT INTO cuentas (camarero,mesa,comensales) VALUES (?,?,?);";
    private String queryConsultarCuenta = "select id_cuenta,fecha_hora,camarero,comensales from cuentas where mesa=? and fecha_salida is null;";
    private String queryConsultarCamareros = "select id_empleado,nombre  from empleados where departamento=3;";
    private String queryConsultarEmpleadoUsuario = "select e.nombre from empleados as e inner join usuarios as u on e.id_empleado = u.empleado where u.id_usuario=?;;";
    private String queryEnviarPedido = "insert into pedidos values(?,?,?) on duplicate key update cantidad=cantidad+?;";
    private String funtionEliminarProducto = "select  BORRAR_PRODUCTO(?,?,?);";
    private String cerrarCuenta = "update cuentas set metodo_pago=?, fecha_salida=CURRENT_TIMESTAMP where id_cuenta=?";
    //PREPAREDSTATEMENT
    private PreparedStatement preconsultarEstablecimientos;
    private PreparedStatement preGetUsuarios;
    private PreparedStatement preConsultarProductos;
    private PreparedStatement preComprobarPasswordUsuario;
    private PreparedStatement preConsultarMesas;
    private PreparedStatement preConsultarSalas;
    private PreparedStatement consultarMesasPorSala;
    private PreparedStatement preConsultarProductosPorGrupo;
    private PreparedStatement preConsultarGruposProductos;
    private PreparedStatement preConsultarPedido;
    private PreparedStatement preConsultarCuenta;
    private PreparedStatement preConsultarCamareros;
    private PreparedStatement preConsultarEmpleadoUsuario;
    private PreparedStatement preEnviarPedido;
    private PreparedStatement preEliminarProducto;
    private PreparedStatement preInsertarNuevaCuenta;
    private PreparedStatement preCerrarCuenta;
    //CONEXION
    ConexionBBDD bbdd;

    /**
     * Crea una instancia de ControllerBBDD.
     *
     * Inicializa la conexión a la base de datos y carga los PreparedStatements
     * necesarios.
     */
    public ControllerBBDD() {

        bbdd = new ConexionBBDD();
        try {
            cargarPreparedStatements();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
    }

    /**
     * Carga los PreparedStatements necesarios para realizar consultas y
     * modificaciones en la base de datos.
     *
     * @throws SQLException Si ocurre un error al crear alguno de los
     * PreparedStatements.
     */
    private void cargarPreparedStatements() throws SQLException {
        preGetUsuarios = bbdd.getCon().prepareStatement(queryGetUsuarios);

        preConsultarProductos = bbdd.getCon().prepareStatement(queryConsultarProductos);
        preComprobarPasswordUsuario = bbdd.getCon().prepareStatement(queryComprobarPasswordUsuario);
        preConsultarMesas = bbdd.getCon().prepareStatement(queryConsultarMesas);
        preConsultarSalas = bbdd.getCon().prepareStatement(queryConsultarSalas);
        consultarMesasPorSala = bbdd.getCon().prepareStatement(queryConsultarMesasPorSala);
        preconsultarEstablecimientos = bbdd.getCon().prepareStatement(queryConsultarEstablecimientos);
        preConsultarProductosPorGrupo = bbdd.getCon().prepareStatement(queryConsultarProductosPorGrupo);
        preConsultarGruposProductos = bbdd.getCon().prepareStatement(queryConsultarGruposProductos);
        preConsultarPedido = bbdd.getCon().prepareStatement(queryConsultarPedido);
        preConsultarCuenta = bbdd.getCon().prepareStatement(queryConsultarCuenta);
        preConsultarCamareros = bbdd.getCon().prepareStatement(queryConsultarCamareros);
        preConsultarEmpleadoUsuario = bbdd.getCon().prepareStatement(queryConsultarEmpleadoUsuario);
        preEnviarPedido = bbdd.getCon().prepareStatement(queryEnviarPedido);
        preEliminarProducto = bbdd.getCon().prepareStatement(funtionEliminarProducto);
        preInsertarNuevaCuenta = bbdd.getCon().prepareStatement(queryInsertarNuevaCuenta, Statement.RETURN_GENERATED_KEYS);
        preCerrarCuenta = bbdd.getCon().prepareStatement(cerrarCuenta);
    }

    /**
     *
     * Devuelve un objeto ResultSet que contiene los usuarios almacenados en la
     * base de datos.
     *
     * @return objeto ResultSet que contiene los usuarios almacenados en la base
     * de datos.
     */
    public ResultSet cargarUsuarios() {

        try {
            return preGetUsuarios.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }

    }

    /**
     * Consulta todos los establecimientos en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarEstablecimientos() {
        try {
            return preconsultarEstablecimientos.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta todas las salas en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarSalas() {
        try {
            return preConsultarSalas.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta todos los camareros en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarCamareros() {
        try {
            return preConsultarCamareros.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta el empleado asociado a un usuario específico.
     *
     * @param id_usuario el ID del usuario.
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarEmpleadoUsuario(int id_usuario) {
        try {
            preConsultarEmpleadoUsuario.setInt(1, id_usuario);
            return preConsultarEmpleadoUsuario.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Inserta una nueva cuenta en la base de datos.
     *
     * @param id_camarero el ID del camarero asociado a la cuenta.
     * @param id_mesa el ID de la mesa asociada a la cuenta.
     * @param comensales el número de comensales en la cuenta.
     * @return un objeto ResultSet que contiene los resultados de la inserción,
     * o null si ocurre una excepción.
     */
    public ResultSet insertarNuevaCuenta(int id_camarero, int id_mesa, int comensales) {
        try {
            preInsertarNuevaCuenta.setInt(1, id_camarero);
            preInsertarNuevaCuenta.setInt(2, id_mesa);
            preInsertarNuevaCuenta.setInt(3, comensales);
            preInsertarNuevaCuenta.executeUpdate();
            return preInsertarNuevaCuenta.getGeneratedKeys();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta una cuenta específica en la base de datos.
     *
     * @param id_mesa el ID de la mesa asociada a la cuenta.
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarCuenta(int id_mesa) {
        try {
            preConsultarCuenta.setInt(1, id_mesa);
            return preConsultarCuenta.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta un pedido específico en la base de datos.
     *
     * @param id_cuenta el ID de la cuenta asociada al pedido.
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarPedido(int id_cuenta) {
        try {
            preConsultarPedido.setInt(1, id_cuenta);
            return preConsultarPedido.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta todos los grupos de productos en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarGruposProductos() {
        try {
            return preConsultarGruposProductos.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta todos los productos en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarProductos() {
        try {
            return preConsultarProductos.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta todas las mesas en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarMesas() {
        try {
            return preConsultarMesas.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta los productos de un grupo específico en la base de datos.
     *
     * @param id_sala el ID de la sala asociada al grupo de productos.
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarProductosPorGrupo(int id_sala) {
        try {
            preConsultarProductosPorGrupo.setInt(1, id_sala);
            return preConsultarProductosPorGrupo.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Consulta las mesas de una sala específica en la base de datos.
     *
     * @param id_sala el ID de la sala asociada a las mesas.
     * @return un objeto ResultSet que contiene los resultados de la consulta, o
     * null si ocurre una excepción.
     */
    public ResultSet consultarMesasPorSala(int id_sala) {
        try {
            consultarMesasPorSala.setInt(1, id_sala);
            return consultarMesasPorSala.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Carga todas las mesas en la base de datos.
     *
     * @param sala el número de sala a cargar.
     * @return un objeto ResultSet que contiene los resultados de la carga, o
     * null si ocurre una excepción.
     */
    public ResultSet cargarMesas(int sala) {
        try {
            return preConsultarMesas.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     * Carga todos los productos en la base de datos.
     *
     * @return un objeto ResultSet que contiene los resultados de la carga, o
     * null si ocurre una excepción.
     */
    public ResultSet cargarProductos() {
        try {
            return preConsultarProductos.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    /**
     *
     * Comprueba si una contraseña coincide con un usuario en la base de datos.
     *
     * @param usuario El nombre de usuario a comprobar.
     * @param contraseña La contraseña a comprobar.
     * @return true si la contraseña coincide con el usuario, false en caso
     * contrario.
     */
    public boolean comprobarPasswordUsuario(String usuario, String contraseña) {

        try {
            preComprobarPasswordUsuario.setString(1, usuario);
            preComprobarPasswordUsuario.setString(2, contraseña);
            boolean coincide = false;
            if (calcularRows(preComprobarPasswordUsuario.executeQuery()) == 1) {
                coincide = true;
            }
            return coincide;
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return false;
        }

    }

    /**
     * Envía un pedido a la base de datos.
     *
     * @param producto el ID del producto a enviar.
     * @param cuenta el ID de la cuenta asociada al pedido.
     * @param cantidad la cantidad del producto a enviar.
     * @return true si el pedido se envió correctamente, false si ocurre una
     * excepción.
     */
    public boolean enviarPedido(int producto, int cuenta, int cantidad) {
        try {
            preEnviarPedido.setInt(1, producto);
            preEnviarPedido.setInt(2, cuenta);
            preEnviarPedido.setInt(3, cantidad);
            preEnviarPedido.setInt(4, cantidad);
            preEnviarPedido.executeUpdate();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return false;
        }
        return true;
    }

    /**
     * Cierra una cuenta en la base de datos.
     *
     * @param metodoPago el método de pago asociado al cierre de la cuenta.
     * @param idCuenta el ID de la cuenta a cerrar.
     * @return true si la cuenta se cerró correctamente, false si ocurre una
     * excepción.
     */
    public boolean cerrarCuenta(String metodoPago, int idCuenta) {
        try {
            preCerrarCuenta.setString(1, metodoPago);
            preCerrarCuenta.setInt(2, idCuenta);
            preCerrarCuenta.executeUpdate();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return false;
        }
        return true;
    }

    /**
     * Elimina un producto de una cuenta en la base de datos.
     *
     * @param producto el ID del producto a eliminar.
     * @param cuenta el ID de la cuenta asociada al producto.
     * @param cantidad la cantidad del producto a eliminar.
     * @return true si el producto se eliminó correctamente, false si ocurre una
     * excepción.
     */
    public boolean eliminarProducto(int producto, int cuenta, int cantidad) {
        try {
            preEliminarProducto.setInt(1, producto);
            preEliminarProducto.setInt(2, cuenta);
            preEliminarProducto.setInt(3, cantidad);
            preEliminarProducto.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return false;
        }
        return true;
    }

    /**
     * Calcula el número de filas en un objeto ResultSet.
     *
     * @param resultSet el objeto ResultSet a calcular.
     * @return el número de filas en el ResultSet, -1 si ocurre una excepción.
     */
    public int calcularRows(ResultSet resultSet) {
        int numFilas = 0;
        try {
            while (resultSet.next()) {
                numFilas++;
            }
        } catch (SQLException ex) {
            return -1;
        }
        return numFilas;
    }
}
