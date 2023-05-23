package swing;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ButtonRedondeado extends JButton {

    public ButtonRedondeado() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public ButtonRedondeado(String texto) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setText(texto);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

//     if (this.getIcon() != null) {
//    int iconWidth = this.getWidth();
//    int iconHeight = this.getHeight();
//    Image iconImage = ((ImageIcon) this.getIcon()).getImage();
//    Image resizedImage = iconImage.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
//    g2.drawImage(resizedImage, 0, 0, null);
//}

        int width = getWidth() - 1;
        int height = getHeight() - 1;
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, height, height));
        g2.dispose();
        super.paintComponent(grphcs);
    }

}
