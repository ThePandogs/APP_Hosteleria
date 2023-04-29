package Swing;

import java.awt.Color;
import javax.swing.JPanel;

public class PanelComponentes extends JPanel {

    public PanelComponentes() {
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
    }
}
