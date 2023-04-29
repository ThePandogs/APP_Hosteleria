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

/**
 *
 * @author a14carlosfd
 */
public class ControllerBBDD {

    private LogExcepcion logExcepcion = new LogExcepcion();
    private Interfaz interfaz;
    //QUERYS
    private String queryGetUsuarios = "select id_usuario,usuario from usuarios;";

    private String queryAnadirSala = "insert into salas values (null,'?')";
    private String queryConsultarProductos = "select * from productos;";

    //PREPAREDSTATEMENT
    private PreparedStatement preGetUsuarios;
    private PreparedStatement preConsultarProductos;
    private PreparedStatement preAnadirSala;
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
    }

    public ResultSet cargarUsuarios() {

        try {
            return preGetUsuarios.executeQuery();
        } catch (SQLException ex) {
            logExcepcion.anadirExcepcionLog(ex);
            return null;
        }

    }

    public int calcularRows(ResultSet resulSet) throws SQLException {

        int numFilas = 0;

        while (resulSet.next()) {
            numFilas++;
        }

        return numFilas;
    }
}
