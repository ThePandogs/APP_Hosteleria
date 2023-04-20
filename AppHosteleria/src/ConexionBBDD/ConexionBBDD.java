/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBBDD;

import Log.LogExcepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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

    //QUERYS
    private String queryAnadirSala = "insert into salas values (null,'?')";
    private String queryConsultarProductos = "select * from productos;";

    // <editor-fold defaultstate="collapsed" desc="GettersAndSetters">       
    public String getQueryAnadirSala() {
        return queryAnadirSala;
    }

    public String getQueryConsultarProductos() {
        return queryConsultarProductos;
    }   // </editor-fold>  

    //PREPAREDSTATEMENT
    private PreparedStatement preConsultarProductos;
    private PreparedStatement preAnadirSala;

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
            cargarPreparedStatements();
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

    /**
     * A partir de una Query realiza la consulta a la BBDD.
     *
     * @param query a realizar.
     * @return Retorna los datos resultantes, si retorna null, sifnifica que
     * arrojo la excepcion "SQLException".
     */
    public ResultSet consulta(String query) {
        try {

            return stmt.executeQuery(query);

        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
        return null;

    }

    /**
     * A partir de una instruccion realiza la modificacion en * la BBDD.
     *
     * @param instruccion para realizar.
     * @return la cantidad de tuplas afectadas por la instruccion. Si retorna
     * "-1", sifnifica que arrojo la excepcion "SQLException".
     */
    public int modificacion(String instruccion) {
        try {
            return stmt.executeUpdate(instruccion);
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
        }
        return -1;
    }

    private void cargarPreparedStatements() throws SQLException {

        preAnadirSala = con.prepareStatement(queryAnadirSala);
        preConsultarProductos = con.prepareStatement(queryConsultarProductos);
    }

}
