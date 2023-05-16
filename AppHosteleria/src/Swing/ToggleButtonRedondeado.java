package Swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class ToggleButtonRedondeado extends JToggleButton {

    public ToggleButtonRedondeado() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public ToggleButtonRedondeado(String texto) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setText(texto);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelected()) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(getBackground());
        }
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, height, height));
        g2.dispose();
        super.paintComponent(grphcs);
    }
}
