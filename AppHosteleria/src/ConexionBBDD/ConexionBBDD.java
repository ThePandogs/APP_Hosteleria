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
 * @author ThePandogs
 *
 * La clase `ConexionBBDD` se utiliza para establecer una conexión a una base de
 * datos utilizando JDBC.
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
     * Crea una conexión a la base de datos con los datos proporcionados.
     *
     * @param url la ruta de la base de datos
     * @param usuario el nombre de usuario
     * @param clave la contraseña
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
     * Crea una conexión a la base de datos utilizando los datos por defecto.
     */
    public ConexionBBDD() {
        try {
            con = DriverManager.getConnection(BBDDURL, USUARIO, CLAVE);
            stmt = con.createStatement();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
    }

    /**
     * Devuelve la conexión actual.
     *
     * @return la conexión a la base de datos
     */
    public Connection getCon() {
        return con;
    }

    /**
     * Cierra tanto el Statement como la conexión existente.
     *
     * @return `true` si se cerraron correctamente sin lanzar una excepción
     * `SQLException`
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
