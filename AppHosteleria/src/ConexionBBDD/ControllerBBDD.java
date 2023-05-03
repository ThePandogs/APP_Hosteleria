/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBBDD;

import Log.LogExcepcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author a14carlosfd
 */
public class ControllerBBDD {

    private LogExcepcion logExcepcion = new LogExcepcion();

    //QUERYS
    private String queryGetUsuarios = "select id_usuario,usuario,rol from usuarios;";

    private String queryAnadirSala = "insert into salas values (null,?)";
    private String queryConsultarProductos = "select * from productos;";
    private String queryComprobarPasswordUsuario = "SELECT * FROM usuarios WHERE usuario = ? AND pin = ?;";
    private String queryConsultarMesas = "select id_mesa,posicionX,posicionY,tamanoX,tamanoY,disponible from mesas;";
    private String queryConsultarMesasPorSala = "select id_mesa,posicionX,posicionY,tamanoX,tamanoY,disponible from mesas where sala=?;";

    private String queryConsultarSalas = "select id_sala,nombre from salas;";
    private String queryConsultarEstablecimientos = "select id_establecimiento,nombre,direccion,cif,prefijo_telefono,telefono from establecimientos;";
    //PREPAREDSTATEMENT
    private PreparedStatement preconsultarEstablecimientos;
    private PreparedStatement preGetUsuarios;
    private PreparedStatement preConsultarProductos;
    private PreparedStatement preAnadirSala;
    private PreparedStatement preComprobarPasswordUsuario;
    private PreparedStatement preConsultarMesas;
    private PreparedStatement preConsultarSalas;
    private PreparedStatement consultarMesasPorSala;

    // <editor-fold defaultstate="collapsed" desc="GettersAndSetters">   
    // </editor-fold>
    //CONEXION
    ConexionBBDD bbdd;

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
        preAnadirSala = bbdd.getCon().prepareStatement(queryAnadirSala);
        preConsultarProductos = bbdd.getCon().prepareStatement(queryConsultarProductos);
        preComprobarPasswordUsuario = bbdd.getCon().prepareStatement(queryComprobarPasswordUsuario);
        preConsultarMesas = bbdd.getCon().prepareStatement(queryConsultarMesas);
        preConsultarSalas = bbdd.getCon().prepareStatement(queryConsultarSalas);
        consultarMesasPorSala = bbdd.getCon().prepareStatement(queryConsultarMesasPorSala);
        preconsultarEstablecimientos = bbdd.getCon().prepareStatement(queryConsultarEstablecimientos);
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

    public ResultSet consultarEstablecimientos() {

        try {
            return preconsultarEstablecimientos.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    public ResultSet consultarSalas() {

        try {
            return preConsultarSalas.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }

    }

    public ResultSet consultarMesas() {

        try {
            return preConsultarMesas.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }

    }

    public ResultSet consultarMesasPorSala(int id_sala) {

        try {
            consultarMesasPorSala.setInt(1, id_sala);
            return consultarMesasPorSala.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }
    }

    public ResultSet cargarMesas(int sala) {
        try {
            return preConsultarMesas.executeQuery();
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

    public int calcularRows(ResultSet resulSet) {

        int numFilas = 0;

        try {
            while (resulSet.next()) {
                numFilas++;
            }
        } catch (SQLException ex) {
            return -1;
        }

        return numFilas;
    }
}
