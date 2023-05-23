package Swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelRedondeado extends JPanel {

    private Color color;
    private ImageIcon imagen;

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

    public void setImagen(String ruta) {
        this.imagen = redimensionarImagen(ruta);
        repaint();
    }
    
    

    private ImageIcon redimensionarImagen(String ruta) {

        Image image = new ImageIcon(ruta).getImage();
        Image newImage = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        return new ImageIcon(newImage); // Crea un nuevo icono con la imagen redimensionada
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (color == null) {
            g2.setColor(getBackground());
        } else {
            g2.setColor(color);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
        if (imagen != null) {
            g2.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
        g2.dispose();
        super.paint(grphcs);
    }
}
