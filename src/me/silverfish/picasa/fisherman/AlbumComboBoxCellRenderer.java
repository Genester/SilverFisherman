package me.silverfish.picasa.fisherman;

import com.google.gdata.data.photos.AlbumEntry;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 19:55:30
 * To change this template use File | Settings | File Templates.
 */
public class AlbumComboBoxCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof AlbumEntry) {
            return super.getListCellRendererComponent(list, ((AlbumEntry)value).getTitle().getPlainText(), index, isSelected, cellHasFocus);
        } else {
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}
