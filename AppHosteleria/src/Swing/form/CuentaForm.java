package Swing.form;

import Swing.component.ProductoComponent;
import Swing.component.SalaComponent;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import modelo.Gestion;
import modelo.GrupoProducto;
import modelo.Local;
import modelo.Mesa;
import modelo.Producto;
import swing.Button;

public class CuentaForm extends javax.swing.JPanel {

    Gestion gestion;
    Local local;
    ArrayList<ProductoComponent> gruposProductos;

    public CuentaForm(Gestion gestion, Local local) {
        this.local = local;
        gruposProductos = new ArrayList();
        initComponents();
        init();
    }

    private void init() {
        this.setVisible(false);
        cargarTodosProductos();
        if (!gruposProductos.isEmpty()) {
            panelProductos.add(gruposProductos.get(0));
        }
    }

    public void setData(Mesa data) {

    }

    private void cargarTodosProductos() {
        cargarGrupoProductos(local);
        Iterator<ProductoComponent> gruposIte = gruposProductos.iterator();
        while (gruposIte.hasNext()) {
            cargarProductos(gruposIte.next());
        }

    }

    private void cargarGrupoProductos(Local local) {
        Iterator<GrupoProducto> grupoProductos = local.getGruposProductos().iterator();
        while (grupoProductos.hasNext()) {
            addGrupo(grupoProductos.next());
        }
    }

    private void cambiarSala(ProductoComponent nuevoProducto) {
        panelGruposProductos.removeAll();
        panelGruposProductos.add(nuevoProducto);
        panelGruposProductos.revalidate(); // actualiza el layout del panel
        panelGruposProductos.repaint(); // repinta el panel
    }

    private void addGrupo(GrupoProducto data) {
        ProductoComponent grupo = new ProductoComponent();
        grupo.setData(data);
        Button botonSala = new Button(data.getNombre());

        botonSala.addActionListener((ActionEvent e) -> {
            cambiarSala(grupo);
        });
        panelGruposProductos.add(botonSala);
        gruposProductos.add(grupo);
    }

    private void cargarProductos(ProductoComponent producto) {

        Iterator<Producto> grupoProductos = producto.getdataGrupoProducto().getProductos().iterator();
        while (grupoProductos.hasNext()) {
            addProducto(grupoProductos.next(), producto);
        }
    }

    private void addProducto(Producto data, ProductoComponent grupoProducto) {
        SalaComponent producto = new SalaComponent();
        producto.setData(data);
        producto.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {

                    // anadirProducto(data);
                }
            }
        });

        grupoProducto.add(producto);

        grupoProducto.repaint();
        grupoProducto.revalidate();
    }

    private void redimensionar(String url, JLabel label) {

        Image image = new ImageIcon(url).getImage();
        // Image newImage = image.getScaledInstance(boton.getWidth(), boton.getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        Image newImage = image.getScaledInstance((int) label.getPreferredSize().getWidth(), (int) label.getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        ImageIcon newIcon = new ImageIcon(newImage);
        label.setIcon(newIcon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PADRE = new Swing.PanelRedondeado();
        header = new Swing.PanelRedondeado();
        mesa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        panelProductos = new Swing.PanelRedondeado();
        panelGruposProductos = new Swing.PanelRedondeado();

        setOpaque(false);

        PADRE.setBackground(new java.awt.Color(51, 255, 153));
        PADRE.setOpaque(true);

        mesa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mesa.setText("Mesa:");
        mesa.setBorder(null);

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane3.setBorder(null);

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Precio", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaProductos);

        javax.swing.GroupLayout PADRELayout = new javax.swing.GroupLayout(PADRE);
        PADRE.setLayout(PADRELayout);
        PADRELayout.setHorizontalGroup(
            PADRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PADRELayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PADRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PADRELayout.createSequentialGroup()
                        .addGroup(PADRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PADRELayout.createSequentialGroup()
                                .addComponent(panelGruposProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(477, 477, 477)))
                        .addContainerGap())
                    .addGroup(PADRELayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PADRELayout.setVerticalGroup(
            PADRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PADRELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PADRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGruposProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PADRE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PADRE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.PanelRedondeado PADRE;
    private Swing.PanelRedondeado header;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField mesa;
    private Swing.PanelRedondeado panelGruposProductos;
    private Swing.PanelRedondeado panelProductos;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
