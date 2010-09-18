package me.silverfish.picasa.fisherman;

import com.google.gdata.data.photos.AlbumEntry;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.*;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 19:16:29
 * To change this template use File | Settings | File Templates.
 */
public class AlbumComboBoxModel implements ComboBoxModel {

    private final List<AlbumEntry> albums;
    private int selectedIndex;

    public AlbumComboBoxModel(List<AlbumEntry> albums) {
        this.albums = new ArrayList<AlbumEntry>(albums);
        sort(this.albums, new Comparator<AlbumEntry>() {

            @Override
            public int compare(AlbumEntry o1, AlbumEntry o2) {
                return (int)(o1.getDate().getTime() -
                       o2.getDate().getTime());  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        selectedIndex = 0;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedIndex = albums.indexOf(anItem);
    }

    @Override
    public Object getSelectedItem() {
        return albums.get(selectedIndex);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getSize() {
        return albums.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getElementAt(int index) {
        return albums.get(index);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
