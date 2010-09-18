package me.silverfish.picasa.fisherman;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
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

    private FileListTableModel tableModel;

    private List<Integer> numbers;
    private Map<Integer, String> numsToFileNames;
    private Map<String, File> fileNamesToLocalFiles;

    public FishermanMain(PicasaClient client) throws IOException, ServiceException {
        this.client = client;
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    numbers = new ArrayList<Integer>();
                    numsToFileNames = new HashMap<Integer, String>();
                    for (String numString : numbersTextArea.getText().trim().split("[^\\d]+")) {
                        numbers.add(Integer.parseInt(numString));
                    }
                    Collections.sort(numbers);
                    tableModel.setNumbers(numbers);
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
                    tableModel.setNumToFileNames(numsToFileNames);
                    matchButton.setEnabled(true);
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServiceException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        matchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser sourceDirFileChooser = new JFileChooser();
                sourceDirFileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "Directories";
                    }
                });
                sourceDirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (sourceDirFileChooser.showOpenDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
                    File sourceDir = sourceDirFileChooser.getSelectedFile();
                    fileNamesToLocalFiles = new HashMap<String, File>();
                    File sourceFile;
                    for (String fileName : numsToFileNames.values()) {
                        sourceFile = sourceDir.listFiles((FilenameFilter) FileFilterUtils.prefixFileFilter(
                                                FilenameUtils.getBaseName(fileName)))[0];
                        fileNamesToLocalFiles.put(fileName, sourceFile);
                    }
                    tableModel.setFileNamesToLocalFiles(fileNamesToLocalFiles);
                    saveButton.setEnabled(true);
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser resultDirFileChooser = new JFileChooser();
                resultDirFileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "Directories";
                    }
                });
                resultDirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (resultDirFileChooser.showOpenDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
                    File resultDir = resultDirFileChooser.getSelectedFile();
                    for (File resultFile : fileNamesToLocalFiles.values()) {
                        try {
                            FileUtils.copyFileToDirectory(resultFile, resultDir);
                        } catch (IOException e1) {
                            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        });
    }

    private void createUIComponents() throws IOException, ServiceException {
        albumsComboBox = new JComboBox(new AlbumComboBoxModel(client.getAlbums()));
        albumsComboBox.setEditable(false);
        albumsComboBox.setRenderer(new AlbumComboBoxCellRenderer());

        tableModel = new FileListTableModel();
        resultsTable = new JTable(tableModel);
    }

    public void show() {
        JFrame frame = new JFrame("FishermanMain");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
