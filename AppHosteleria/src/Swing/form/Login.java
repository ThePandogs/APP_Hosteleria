package Swing.form;

import Swing.component.Item;
import Swing.ScrollBar;
import Swing.component.Numero;
import Swing.component.User;
import Swing.model.ModelNumero;
import Swing.model.ModelUser;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class Login extends javax.swing.JPanel {

    public Login() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
    }

    public void addUser(ModelUser data) {
        User user = new User();
        user.setData(data);
        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    System.out.println("le has dado");
                }
            }
        });
        PanelUsuarios.add(user);
        PanelUsuarios.repaint();
        PanelUsuarios.revalidate();
    }

    public void addNumero(ModelNumero data) {
        Numero numero = new Numero();
        numero.setData(data);
        numero.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    System.out.println(numero.getData().getNumero());
                }
            }
        });
        TecladoNumerico.add(numero);
        TecladoNumerico.repaint();
        TecladoNumerico.revalidate();
    }

    public void setSelected(Component item) {
        for (Component com : PanelUsuarios.getComponents()) {
            Item i = (Item) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((Item) item).setSelected(true);
    }

    public Point getPanelItemLocation() {
        Point p = scroll.getLocation();
        return new Point(p.x, p.y - scroll.getViewport().getViewPosition().y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        PanelUsuarios = new Swing.PanelComponentes();
        PanelNumerico = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TecladoNumerico = new Swing.PanelComponentes();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(PanelUsuarios);

        PanelNumerico.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inicio Sesión");

        TecladoNumerico.setLayout(new java.awt.GridLayout(4, 3, 10, 10));

        javax.swing.GroupLayout PanelNumericoLayout = new javax.swing.GroupLayout(PanelNumerico);
        PanelNumerico.setLayout(PanelNumericoLayout);
        PanelNumericoLayout.setHorizontalGroup(
            PanelNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
            .addGroup(PanelNumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TecladoNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelNumericoLayout.setVerticalGroup(
            PanelNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNumericoLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(TecladoNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addGap(94, 94, 94))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(PanelNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scroll)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelNumerico;
    private Swing.PanelComponentes PanelUsuarios;
    private Swing.PanelComponentes TecladoNumerico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
