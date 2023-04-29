package Swing.event;

import Swing.model.ModelItem;
import java.awt.Component;

public interface EventItem {

    public void itemClick(Component com, ModelItem item);
}
