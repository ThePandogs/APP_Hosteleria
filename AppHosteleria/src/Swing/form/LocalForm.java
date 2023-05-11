package Swing.form;

import Swing.component.MesaComponent;
import Swing.component.ComponentsContainer;
import Swing.component.UserComponent;
import iu.Interfaz;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;
import modelo.Gestion;
import modelo.Local;
import modelo.Mesa;
import modelo.Sala;
import swing.ButtonRedondeado;

public class LocalForm extends javax.swing.JPanel {

    private Gestion gestion;
    private CuentaForm cuentaForm;
    private Local local;
    private ArrayList<ComponentsContainer> salas;
    private Interfaz interfaz;
    private Sala salaActual;

    public LocalForm(Gestion gestion, Interfaz interfaz, Local local) {
        this.gestion = gestion;
        this.local = local;
        this.interfaz = interfaz;

        salas = new ArrayList();

        initComponents();
        init();

    }

    private void init() {
        cargarEstablecimiento();
        cuentaForm = new CuentaForm(local, this);
        this.add(cuentaForm, 0);
        panelSala.setColor(new Color(153, 180, 209));
        if (!salas.isEmpty()) {
            cambiarSala(salas.get(0));
        }

    }

    private void cargarEstablecimiento() {
        cargarSalas(local);
        Iterator<ComponentsContainer> salasIte = salas.iterator();
        while (salasIte.hasNext()) {
            cargarMesas(salasIte.next());
        }

    }

    private void cargarSalas(Local local) {
        Iterator<Sala> locales = local.getSalas().iterator();
        while (locales.hasNext()) {
            addSala(locales.next());
        }
    }

    private void cambiarSala(ComponentsContainer nuevaSala) {
        salaActual = nuevaSala.getDataSala();
        panelSala.removeAll();
        panelSala.add(nuevaSala);
        panelSala.revalidate(); // actualiza el layout del panel
        panelSala.repaint(); // repinta el panel
    }

    private void addSala(Sala data) {
        ComponentsContainer sala = new ComponentsContainer();
        sala.setColor(new Color(180, 180, 209));
        sala.setData(data);
        ButtonRedondeado botonSala = new ButtonRedondeado(data.getNombre());

        botonSala.addActionListener((ActionEvent e) -> {
            cambiarSala(sala);
        });

        listaSalas.add(botonSala);
        salas.add(sala);

    }

    private void cargarMesas(ComponentsContainer sala) {

        Iterator<Mesa> mesas = sala.getDataSala().getMesas().iterator();
        while (mesas.hasNext()) {
            addMesa(mesas.next(), sala);
        }
    }

    private void addMesa(Mesa data, ComponentsContainer sala) {
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

        sala.add(mesa);

        sala.repaint();
        sala.revalidate();
    }

    private void abrirMesa(Mesa data) {
        cuentaForm.setData(data);
        
        panelSalas.setVisible(false);
        cuentaForm.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    public void cerrarMesa() {
        cuentaForm.setVisible(false);
        panelSalas.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    public void setSelected(Component user) {
        for (Component com : panelSala.getComponents()) {
            UserComponent i = (UserComponent) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((UserComponent) user).setSelected(true);

    }

    public Sala getSalaActual() {
        return salaActual;
    }

    public Gestion getGestion() {
        return gestion;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSalas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaSalas = new Swing.PanelComponentes();
        panelSala = new Swing.PanelRedondeado();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.OverlayLayout(this));

        panelSalas.setOpaque(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        java.awt.GridBagLayout listaSalasLayout = new java.awt.GridBagLayout();
        listaSalasLayout.columnWeights = new double[] {1.0};
        listaSalas.setLayout(listaSalasLayout);
        jScrollPane1.setViewportView(listaSalas);

        panelSala.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout panelSalasLayout = new javax.swing.GroupLayout(panelSalas);
        panelSalas.setLayout(panelSalasLayout);
        panelSalasLayout.setHorizontalGroup(
            panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSala, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSalasLayout.setVerticalGroup(
            panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(panelSala, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(panelSalas);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private Swing.PanelComponentes listaSalas;
    private Swing.PanelRedondeado panelSala;
    private javax.swing.JPanel panelSalas;
    // End of variables declaration//GEN-END:variables
}
