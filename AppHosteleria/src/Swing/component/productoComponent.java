package Swing.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import modelo.GrupoProducto;
import modelo.Producto;

public class ProductoComponent extends javax.swing.JPanel {

    private Producto dataProducto;
    private GrupoProducto dataGrupoProducto;

    public ProductoComponent() {
        initComponents();
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

         // Establecer alineación centrada
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
    }

    public void setData(Producto data) {
        this.dataProducto = data;
        nombre.setText(data.getNombre());
      

    }

    public void setData(GrupoProducto data) {
        this.dataGrupoProducto = data;
        nombre.setText(data.getNombre());
    }

    public Producto getDataProducto() {
        return dataProducto;
    }

    public GrupoProducto getdataGrupoProducto() {
        return dataGrupoProducto;
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(242, 242, 242));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.dispose();
        super.paint(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombre = new javax.swing.JTextArea();

        nombre.setEditable(false);
        nombre.setColumns(25);
        nombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nombre.setLineWrap(true);
        nombre.setRows(2);
        nombre.setWrapStyleWord(true);
        nombre.setAutoscrolls(false);
        nombre.setBorder(null);
        nombre.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        nombre.setFocusable(false);
        nombre.setOpaque(false);
        nombre.setPreferredSize(new java.awt.Dimension(80, 40));
        nombre.setSelectionColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 55, Short.MAX_VALUE)
                .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea nombre;
    // End of variables declaration//GEN-END:variables
}
