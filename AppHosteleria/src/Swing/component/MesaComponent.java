package Swing.component;

import Swing.form.LocalForm;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Mesa;

public class MesaComponent extends javax.swing.JPanel {

    private boolean selected;
    private int startX, startY;
    private int minWidth = 100;
    private int minHeight = 100;
    private boolean resizing = false;
    private int resizeMargin = 5;
    private Mesa data;
    private LocalForm local;

    public MesaComponent(LocalForm local) {
        initComponents();
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.local = local;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!local.getToggleButtonRedondeado2().isSelected()) {
                    return;
                }
                startX = e.getX();
                startY = e.getY();
                if (isOnResizeMargin(e.getPoint())) {
                    resizing = true;
                    setCursor(getCursorForPoint(e.getPoint()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!local.getToggleButtonRedondeado2().isSelected()) {
                    return;
                }
                int deltaX = e.getX() - startX;
                int deltaY = e.getY() - startY;
                if (!resizing) {

                    setLocation(getLocation().x + deltaX, getLocation().y + deltaY);

                } else {
                    resize(e.getPoint());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!local.getToggleButtonRedondeado2().isSelected()) {
                    return;
                }
                if (isOnResizeMargin(e.getPoint())) {
                    setCursor(getCursorForPoint(e.getPoint()));
                } else {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    private boolean isOnResizeMargin(Point point) {
        return point.x < resizeMargin || point.y < resizeMargin
                || point.x > getWidth() - resizeMargin || point.y > getHeight() - resizeMargin;
    }

    private Cursor getCursorForPoint(Point point) {
        int x = point.x;
        int y = point.y;
        if (x < resizeMargin && y < resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
        } else if (x < resizeMargin && y > getHeight() - resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
        } else if (x > getWidth() - resizeMargin && y < resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
        } else if (x > getWidth() - resizeMargin && y > getHeight() - resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
        } else if (x < resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
        } else if (x > getWidth() - resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
        } else if (y < resizeMargin) {
            return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
        } else {
            return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
        }
    }

    private void resize(Point point) {
        int deltaX = point.x - startX;
        int deltaY = point.y - startY;
        int newWidth = getWidth();
        int newHeight = getHeight();
        int newX = getX();
        int newY = getY();
        Cursor resizeCursor = this.getCursor();
        if (resizeCursor == Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)) {
            newY += deltaY;
            newHeight -= deltaY;
            if (newHeight < minHeight) {
                newHeight = minHeight;
                newY = getY() + getHeight() - minHeight;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)) {
            newHeight += deltaY;
            if (newHeight < minHeight) {
                newHeight = minHeight;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)) {
            newX += deltaX;
            newWidth -= deltaX;
            if (newWidth < minWidth) {
                newWidth = minWidth;
                newX = getX() + getWidth() - minWidth;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
            newWidth += deltaX;
            if (newWidth < minWidth) {
                newWidth = minWidth;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)) {
            newX += deltaX;
            newY += deltaY;
            newWidth -= deltaX;
            newHeight -= deltaY;
            if (newWidth < minWidth) {
                newWidth = minWidth;
                newX = getX() + getWidth() - minWidth;
            }
            if (newHeight < minHeight) {
                newHeight = minHeight;
                newY = getY() + getHeight() - minHeight;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR)) {
            newY += deltaY;
            newWidth += deltaX;
            newHeight -= deltaY;
            if (newWidth < minWidth) {
                newWidth = minWidth;
            }
            if (newHeight < minHeight) {
                newHeight = minHeight;
                newY = getY() + getHeight() - minHeight;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR)) {
            newX += deltaX;
            newWidth -= deltaX;
            newHeight += deltaY;
            if (newWidth < minWidth) {
                newWidth = minWidth;
                newX = getX() + getWidth() - minWidth;
            }
            if (newHeight < minHeight) {
                newHeight = minHeight;
            }
        } else if (resizeCursor == Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)) {
            newWidth += deltaX;
            newHeight += deltaY;
            if (newWidth < minWidth) {
                newWidth = minWidth;
            }
            if (newHeight < minHeight) {
                newHeight = minHeight;
            }
        }

        setBounds(newX, newY, newWidth, newHeight);

        setBounds(newX, newY, newWidth, newHeight);

        startX = point.x;
        startY = point.y;
        setBounds(getX(), getY(), newWidth, newHeight);
        numeroMesa.setBounds(numeroMesa.getX(), numeroMesa.getY(), newWidth, newHeight);

    }

// <editor-fold defaultstate="collapsed" desc="Getter_Setter">     
    public Mesa getData() {
        return data;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void setData(Mesa data) {
        this.data = data;
        numeroMesa.setText(String.valueOf(data.getNumero()));
        setBounds(data.getX(), data.getY(), data.getWidth(), data.getHeight());

    }
// </editor-fold>  

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(242, 242, 242));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        if (selected) {
            g2.setColor(new Color(94, 156, 255));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        g2.dispose();
        super.paint(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numeroMesa = new javax.swing.JLabel();

        setOpaque(false);

        numeroMesa.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        numeroMesa.setForeground(new java.awt.Color(76, 76, 76));
        numeroMesa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroMesa.setText("Mesa");
        numeroMesa.setMaximumSize(new java.awt.Dimension(100, 32));
        numeroMesa.setMinimumSize(new java.awt.Dimension(100, 32));
        numeroMesa.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroMesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel numeroMesa;
    // End of variables declaration//GEN-END:variables
}
