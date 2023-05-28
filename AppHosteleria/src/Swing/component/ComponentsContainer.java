package Swing.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import modelo.GrupoProducto;
import modelo.Sala;

/**
 * @author ThePandogs
 * 
 * Clase que representa un contenedor de componentes. Extiende
 * javax.swing.JPanel.
 */
public class ComponentsContainer extends javax.swing.JPanel {

    private Color color;
    private Sala dataSala;
    private GrupoProducto dataGrupoProducto;

    /**
     * Constructor predeterminado de ComponentsContainer. Inicializa el
     * contenedor y establece su opacidad como transparente. Establece un layout
     * nulo (null) para permitir el posicionamiento absoluto de los componentes.
     */
    public ComponentsContainer() {
        init();
        setOpaque(false);
        setLayout(null);

    }

    /**
     * Constructor de ComponentsContainer con parámetros. Inicializa el
     * contenedor con un color y un layout específicos.
     *
     * @param color el color del contenedor.
     * @param layaot el layout a utilizar para organizar los componentes.
     */
    public ComponentsContainer(Color color, LayoutManager layaot) {
        init();

        this.color = color;
        setLayout(layaot);

    }

    private void init() {
        initComponents();
        setOpaque(false);

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setData(Sala data) {
        this.dataSala = data;

    }

    public Sala getDataSala() {
        return dataSala;
    }

    public GrupoProducto getDataGrupo() {
        return dataGrupoProducto;
    }

    public void setData(GrupoProducto data) {
        this.dataGrupoProducto = data;

    }

    /**
     * Sobrescribe el método paint para personalizar la apariencia del
     * contenedor. Este método dibuja un rectángulo redondeado en el fondo del
     * contenedor con el color especificado. Utiliza anti-aliasing para obtener
     * bordes suavizados.
     *
     * @param grphcs el objeto Graphics utilizado para dibujar.
     */
    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (color != null) {
            g2.setColor(color);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paint(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
