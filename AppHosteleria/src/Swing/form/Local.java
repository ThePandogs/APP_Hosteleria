package Swing.form;

import ConexionBBDD.ControllerBBDD;
import Swing.PanelRedondeado;
import Swing.component.MesaComponent;
import Swing.component.SalaComponent;
import Swing.component.UserComponent;
import iu.Interfaz;
import java.awt.Color;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import modelo.Mesa;
import modelo.Sala;

public class Local extends javax.swing.JPanel {

    ControllerBBDD controllerBBDD;

    public Local(ControllerBBDD controllerBBDD) {
        this.controllerBBDD = controllerBBDD;
        initComponents();

        init();
        salas.setColor(new Color(46, 144, 232));
    }

    private void init() {
        cargarMesas();

    }

    public void cargarSalas() {

        try {
            ResultSet consulta = controllerBBDD.cargarSalas();

            while (consulta.next()) {

                addSala(new Sala(consulta.getInt(1), consulta.getString(2)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarMesas() {

        try {
            ResultSet consulta = controllerBBDD.cargarMesas();

            while (consulta.next()) {

                addMesa(new Mesa(consulta.getInt(1), consulta.getInt(2), consulta.getInt(3), consulta.getInt(4), consulta.getInt(5), consulta.getBoolean(6)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addSala(Sala data) {
        SalaComponent sala = new SalaComponent();
        sala.setData(data);

        salas.add(sala);
        salas.repaint();
        salas.revalidate();
    }

    public void addMesa(Mesa data) {
        MesaComponent mesa = new MesaComponent();
        mesa.setData(data);
        mesa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {

                    abrirMesa(data);

                }
            }
        });

        salas.add(mesa);
        salas.repaint();
        salas.revalidate();
    }

    private void abrirMesa(Mesa data) {
    }

    public void setSelected(Component user) {
        for (Component com : salas.getComponents()) {
            UserComponent i = (UserComponent) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((UserComponent) user).setSelected(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelComponentes2 = new Swing.PanelComponentes();
        salas = new Swing.PanelRedondeado();

        setOpaque(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(panelComponentes2);

        javax.swing.GroupLayout salasLayout = new javax.swing.GroupLayout(salas);
        salas.setLayout(salasLayout);
        salasLayout.setHorizontalGroup(
            salasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        salasLayout.setVerticalGroup(
            salasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private Swing.PanelComponentes panelComponentes2;
    private Swing.PanelRedondeado salas;
    // End of variables declaration//GEN-END:variables
}
