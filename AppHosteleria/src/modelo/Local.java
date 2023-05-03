/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import ConexionBBDD.ControllerBBDD;
import iu.Interfaz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThePandogs
 */
public class Local {

    int id_establecimiento;
    String nombre;
    String direccion;
    String cif;
    String prefijo_telefono;
    String telefono;
    List<Sala> salas;
    List<Camarero> camareros;
    List<GrupoProducto> gruposProductos;

    public Local(int id_establecimiento, String nombre, String direccion, String cif, String prefijo_telefono, String telefono) {

        this.id_establecimiento = id_establecimiento;
        this.nombre = nombre;
        this.direccion = direccion;
        this.cif = cif;
        this.prefijo_telefono = prefijo_telefono;
        this.telefono = telefono;

        salas = new ArrayList();
        camareros = new ArrayList();
        gruposProductos = new ArrayList();

    }

    public void anadirSala(Sala sala) {
        salas.add(sala);
    }

    public void borrarSala(Sala sala) {
        salas.remove(sala);
    }

    public void anadirGrupoProducto(GrupoProducto grupo) {
        gruposProductos.add(grupo);
    }

    public void cierreCaja() {
    }

    public int getId_establecimiento() {
        return id_establecimiento;
    }

    public void setId_establecimiento(int id_establecimiento) {
        this.id_establecimiento = id_establecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPrefijo_telefono() {
        return prefijo_telefono;
    }

    public void setPrefijo_telefono(String prefijo_telefono) {
        this.prefijo_telefono = prefijo_telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Camarero> getCamareros() {
        return camareros;
    }

    public void setCamareros(List<Camarero> camareros) {
        this.camareros = camareros;
    }

    public List<GrupoProducto> getGruposProductos() {
        return gruposProductos;
    }

    public void setGruposProductos(List<GrupoProducto> gruposProductos) {
        this.gruposProductos = gruposProductos;
    }

}
