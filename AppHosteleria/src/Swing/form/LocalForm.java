package Swing.form;


import Swing.component.MesaComponent;
import Swing.component.SalaComponent;
import Swing.component.UserComponent;
import iu.Interfaz;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.SwingUtilities;
import modelo.Gestion;
import modelo.Local;
import modelo.Mesa;
import modelo.Sala;
import swing.Button;

public class LocalForm extends javax.swing.JPanel {

    Gestion gestion;
    Local local;
    ArrayList<SalaComponent> salas;

    public LocalForm(Gestion gestion, Interfaz interfaz, Local local) {
        this.gestion = gestion;
        this.local = local;
        salas = new ArrayList();
        initComponents();
        init();

       
    }

    private void init() {
        cargarEstablecimiento();

        panelSalas.setColor(new Color(46, 144, 232));
        panelSalas.add(salas.get(0));

    }

    private void cargarEstablecimiento() {
        cargarSalas(local);
        Iterator<SalaComponent> salasIte = salas.iterator();
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

    private void cambiarSala(SalaComponent nuevaSala) {

        panelSalas.removeAll();
        panelSalas.add(nuevaSala);
        panelSalas.revalidate(); // actualiza el layout del panel
        panelSalas.repaint(); // repinta el panel
    }

    private void addSala(Sala data) {
        SalaComponent sala = new SalaComponent();
        sala.setColor(new Color(46, 144, 232));
        sala.setData(data);
        Button botonSala = new Button(data.getNombre());
      
        botonSala.addActionListener((ActionEvent e) -> {
            cambiarSala(sala);
        });

        listaSalas.add(botonSala);
        salas.add(sala);

    }

    private void cargarMesas(SalaComponent sala) {

        Iterator<Mesa> mesas = sala.getData().getMesas().iterator();
        while (mesas.hasNext()) {
            addMesa(mesas.next(), sala);
        }
    }

    private void addMesa(Mesa data, SalaComponent sala) {
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
    }

    public void setSelected(Component user) {
        for (Component com : panelSalas.getComponents()) {
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
        listaSalas = new Swing.PanelComponentes();
        panelSalas = new Swing.PanelRedondeado();

        setOpaque(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        java.awt.GridBagLayout listaSalasLayout = new java.awt.GridBagLayout();
        listaSalasLayout.columnWeights = new double[] {1.0};
        listaSalas.setLayout(listaSalasLayout);
        jScrollPane1.setViewportView(listaSalas);

        panelSalas.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSalas, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelSalas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private Swing.PanelComponentes listaSalas;
    private Swing.PanelRedondeado panelSalas;
    // End of variables declaration//GEN-END:variables
}
