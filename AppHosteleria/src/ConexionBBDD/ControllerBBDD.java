/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBBDD;

import Log.LogExcepcion;
import iu.Interfaz;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a14carlosfd
 */
public class ControllerBBDD {

    private LogExcepcion logExcepcion = new LogExcepcion();
    private Interfaz interfaz;
    //QUERYS
    private String queryGetUsuarios = "select id_usuario,usuario from usuarios;";

    private String queryAnadirSala = "insert into salas values (null,?)";
    private String queryConsultarProductos = "select * from productos;";
    private String queryComprobarPasswordUsuario = "SELECT * FROM usuarios WHERE usuario = ? AND pin = ?";

    //PREPAREDSTATEMENT
    private PreparedStatement preGetUsuarios;
    private PreparedStatement preConsultarProductos;
    private PreparedStatement preAnadirSala;
    private PreparedStatement preComprobarPasswordUsuario;
    // <editor-fold defaultstate="collapsed" desc="GettersAndSetters">   
    // </editor-fold>

    //CONEXION
    ConexionBBDD bbdd;

    public ControllerBBDD(Interfaz interfaz) {
        this.interfaz = interfaz;
        bbdd = new ConexionBBDD();
        try {
            cargarPreparedStatements();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
    }

    private void cargarPreparedStatements() throws SQLException {
        preGetUsuarios = bbdd.getCon().prepareStatement(queryGetUsuarios, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        preAnadirSala = bbdd.getCon().prepareStatement(queryAnadirSala);
        preConsultarProductos = bbdd.getCon().prepareStatement(queryConsultarProductos);
        preComprobarPasswordUsuario = bbdd.getCon().prepareStatement(queryComprobarPasswordUsuario);
    }

    public ResultSet cargarUsuarios() {

        try {
            return preGetUsuarios.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }

    }

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
