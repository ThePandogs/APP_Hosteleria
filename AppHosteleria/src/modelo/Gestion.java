/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ControllerBBDD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a14carlosfd
 */
public class Gestion {

    ControllerBBDD controllerBBDD = new ControllerBBDD();

    ArrayList<Local> locales;

    public Gestion() {

        try {
            cargarLocales();
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

    private void cargarSalas(Local local) throws SQLException {

        ResultSet consulta = controllerBBDD.consultarSalas();

        while (consulta.next()) {
            local.getSalas().add(new Sala(consulta.getInt("id_sala"),
                    consulta.getString("nombre")));

        }

    }

    private void cargarMesas(Sala sala) throws SQLException {
        ResultSet consulta = controllerBBDD.consultarMesas();

        while (consulta.next()) {
            sala.getMesas().add(new Mesa(consulta.getInt("idMesa"),
                    consulta.getInt("posicionX"),
                    consulta.getInt("posicionY"),
                    consulta.getInt("width"),
                    consulta.getInt("height"),
                    consulta.getBoolean("disponible")));

        }
    }
}
