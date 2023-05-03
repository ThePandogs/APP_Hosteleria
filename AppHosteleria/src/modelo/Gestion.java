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

    private void cargarTodo() throws SQLException {

        cargarLocales();
        if (!locales.isEmpty()) {
            Iterator<Local> localesITe = locales.iterator();
            while (localesITe.hasNext()) {

                cargarSalas(localesITe.next());

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

    /*carga las mesas, si consulta es null retorna false*/
    private boolean cargarMesas(Sala sala) throws SQLException {
        int id_sala = sala.getId();
        ResultSet consulta = controllerBBDD.consultarMesasPorSala(id_sala);
        if (consulta != null) {
            while (consulta.next()) {
                sala.getMesas().add(new Mesa(consulta.getInt("id_mesa"),
                        consulta.getInt("posicionX"),
                        consulta.getInt("posicionY"),
                        consulta.getInt("tamanoX"),
                        consulta.getInt("tamanoY"),
                        consulta.getBoolean("disponible")));

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
