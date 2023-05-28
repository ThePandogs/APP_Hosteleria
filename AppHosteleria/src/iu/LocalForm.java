package iu;

import Swing.ToggleButtonRedondeado;
import Swing.component.MesaComponent;
import Swing.component.ComponentsContainer;
import Swing.component.UserComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
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

/**
 * 
 * @author ThePandogs
 * 
 * Representa un formulario de local. Contiene la lógica y los componentes
 * necesarios para gestionar un local.
 */
public class LocalForm extends javax.swing.JPanel {

    private Gestion gestion;
    private CuentaForm cuentaForm;
    private Local local;
    private ArrayList<ComponentsContainer> salas;
    private Interfaz interfaz;
    private Sala salaActual;
    private boolean editarSala = false;

    /**
     * Crea una instancia de LocalForm.
     *
     * @param gestion la instancia de Gestion utilizada para gestionar el local
     * @param interfaz la instancia de Interfaz utilizada para la interfaz
     * gráfica
     * @param local el objeto Local que representa el local actual
     */
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
        sala.setColor(new Color(153, 180, 209));
        sala.setData(data);
        ButtonRedondeado botonSala = new ButtonRedondeado(data.getNombre());

        botonSala.addActionListener((ActionEvent e) -> {
            cambiarSala(sala);
        });
        //Le asociamos en layout del panel para que sea redimensiobable
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0; // Permite el redimensionamiento horizontal
        gbc.gridx = 0;
        //gbc.gridy = listaSalas.getComponentCount();
        listaSalas.add(botonSala, gbc);

        salas.add(sala);

    }

    private void cargarMesas(ComponentsContainer sala) {

        Iterator<Mesa> mesas = sala.getDataSala().getMesas().iterator();
        while (mesas.hasNext()) {
            addMesa(mesas.next(), sala);
        }
    }

    private void addMesa(Mesa data, ComponentsContainer sala) {
        MesaComponent mesa = new MesaComponent(this);
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

    /**
     * Cierra la mesa actual y muestra el panel de salas.
     */
    public void cerrarMesa() {
        cuentaForm.setVisible(false);
        panelSalas.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    /**
     * Establece el componente de usuario seleccionado y deselecciona todos los
     * demás componentes en el panel de sala.
     *
     * @param user el componente de usuario que se seleccionará
     */
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

    public boolean isEditarSala() {
        return editarSala;
    }

    public ToggleButtonRedondeado getToggleButtonRedondeado2() {
        return toggleButtonRedondeado2;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSalas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaSalas = new Swing.PanelComponentes();
        panelSala = new Swing.PanelRedondeado();
        panelRedondeado1 = new Swing.PanelRedondeado();
        toggleButtonRedondeado2 = new Swing.ToggleButtonRedondeado();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1138, 814));
        setLayout(new javax.swing.OverlayLayout(this));

        panelSalas.setOpaque(false);
        panelSalas.setPreferredSize(new java.awt.Dimension(1138, 814));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 378));

        listaSalas.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(listaSalas);

        panelSala.setPreferredSize(new java.awt.Dimension(720, 528));
        panelSala.setLayout(new java.awt.GridLayout(1, 0));

        panelRedondeado1.setBackground(new java.awt.Color(255, 255, 255));
        panelRedondeado1.setPreferredSize(new java.awt.Dimension(200, 150));

        toggleButtonRedondeado2.setBackground(new java.awt.Color(102, 153, 255));
        toggleButtonRedondeado2.setForeground(new java.awt.Color(255, 255, 255));
        toggleButtonRedondeado2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/editarSala.png"))); // NOI18N
        toggleButtonRedondeado2.setText("Editar Sala");
        toggleButtonRedondeado2.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        toggleButtonRedondeado2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        toggleButtonRedondeado2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        toggleButtonRedondeado2.setIconTextGap(0);

        javax.swing.GroupLayout panelRedondeado1Layout = new javax.swing.GroupLayout(panelRedondeado1);
        panelRedondeado1.setLayout(panelRedondeado1Layout);
        panelRedondeado1Layout.setHorizontalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggleButtonRedondeado2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRedondeado1Layout.setVerticalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRedondeado1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(toggleButtonRedondeado2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSalasLayout = new javax.swing.GroupLayout(panelSalas);
        panelSalas.setLayout(panelSalasLayout);
        panelSalasLayout.setHorizontalGroup(
            panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSala, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRedondeado1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelSalasLayout.setVerticalGroup(
            panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSalasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRedondeado1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                    .addComponent(panelSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(panelSalas);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private Swing.PanelComponentes listaSalas;
    private Swing.PanelRedondeado panelRedondeado1;
    private Swing.PanelRedondeado panelSala;
    private javax.swing.JPanel panelSalas;
    private Swing.ToggleButtonRedondeado toggleButtonRedondeado2;
    // End of variables declaration//GEN-END:variables
}
