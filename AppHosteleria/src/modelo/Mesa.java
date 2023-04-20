/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.JLabel;

/**
 *
 * @author ThePandogs
 */
public class Mesa {

    private int idMesa;
    private Cuenta cuenta;
    private JLabel representacion;
    private boolean disponible;
    private int x;
    private int y;

    public Mesa(int idMesa, int posicionX, int posicionY, boolean disponible, String imagenURL) {
        this.idMesa = idMesa;
        this.disponible = disponible;
        cuenta = new Cuenta();
        representacion = new JLabel(new javax.swing.ImageIcon(getClass().getResource(imagenURL)));
        representacion.setLocation(posicionX, posicionX);
        this.x = posicionX;
        this.y = posicionY;
    }

    public void cambiarPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        representacion.setLocation(x, y);
    }

    public void cambiarTamano(int ancho, int alto) {

        representacion.setSize(ancho, alto);

    }

}
