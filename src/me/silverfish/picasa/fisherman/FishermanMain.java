package me.silverfish.picasa.fisherman;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 18:15:58
 * To change this template use File | Settings | File Templates.
 */
public class FishermanMain {
    private JComboBox albumsComboBox;
    private JButton findButton;
    private JButton matchButton;
    private JButton saveButton;
    private JTextArea numbersTextArea;
    private JTable resultsTable;
    private JPanel rootPanel;

    private final PicasaClient client;

    private List<Integer> numbers;
    private Map<Integer, String> numsToFileNames;

    public FishermanMain(PicasaClient client) throws IOException, ServiceException {
        this.client = client;
        createUIComponents();
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    numbers = new ArrayList<Integer>();
                    numsToFileNames = new HashMap<Integer, String>();
                    for (String numString : numbersTextArea.getText().split("[^\\d]+")) {
                        numbers.add(Integer.parseInt(numString));
                    }
                    Collections.sort(numbers);
                    System.out.println("Find!");
                    System.out.println("Selected Album: " + (AlbumEntry)albumsComboBox.getSelectedItem());
                    List<PhotoEntry> photos = FishermanMain.this.client.getPhotos((AlbumEntry)albumsComboBox.getSelectedItem());
                    PhotoEntry photo;
                    for (int i = 0; i < photos.size(); i++) {
                        photo = photos.get(i);
                        if (numbers.contains(i+1)) {
                            numsToFileNames.put(i+1, photo.getTitle().getPlainText());
                            System.out.println((i+1) + ": " + photo.getTitle().getPlainText()); 
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServiceException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

    private void createUIComponents() throws IOException, ServiceException {
        albumsComboBox = new JComboBox(new AlbumComboBoxModel(client.getAlbums()));
        albumsComboBox.setEditable(false);
        albumsComboBox.setRenderer(new AlbumComboBoxCellRenderer());
        // TODO: place custom component creation code here
    }

    public void show() {
        JFrame frame = new JFrame("FishermanMain");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
