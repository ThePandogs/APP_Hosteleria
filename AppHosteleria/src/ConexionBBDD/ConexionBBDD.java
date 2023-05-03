/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBBDD;

import Log.LogExcepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author a14carlosfd
 */
public class ConexionBBDD {

    private final String BBDDURL = "jdbc:mysql://localhost/hosteleria";
    private final String USUARIO = "root";
    private final String CLAVE = "toor";

    private Connection con;
    private Statement stmt;

    //LOGGER
    private LogExcepcion logExcepcion = new LogExcepcion();

    /**
     * Crea una conexion a la base de datos con los datos aportados.
     *
     * @param url ruta de la BBDD
     * @param usuario nombre de usuario
     * @param clave Contraseña
     */
    public ConexionBBDD(String url, String usuario, String clave) {
        try {
            con = DriverManager.getConnection(url, usuario, clave);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
    }

    /**
     * Crea una conexion a la base de datos con los datos por defecto.
     */
    public ConexionBBDD() {
        try {
            con = DriverManager.getConnection(BBDDURL, USUARIO, CLAVE);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
    }

    public Connection getCon() {
        return con;
    }

    /**
     * Cierra tanto el Statement como la conexion existente.
     *
     * @return "true" si se realizó sin la excepcion "SQLException".
     */
    public boolean cerrarConexion() {
        try {
            stmt.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
        return false;

    }

   

}
