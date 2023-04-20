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
    private int x;
    private int y;

    public Mesa( int idMesa,int posicionX,int posicionY) {
        this.idMesa = idMesa;
      
        cuenta = new Cuenta();
    }

    public void asignarCamarero(Camarero camarero) {
        this.camarero = camarero;
    }

}
