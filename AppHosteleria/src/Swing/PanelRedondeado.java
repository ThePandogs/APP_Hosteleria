package Swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelRedondeado extends JPanel {

    Color color;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    public PanelRedondeado() {
        setOpaque(false);

    }
    private int round = 20;

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (color == null) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(color);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
        g2.dispose();
        super.paint(grphcs);
    }
}
