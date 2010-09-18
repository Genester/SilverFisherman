package me.silverfish.picasa.fisherman;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 21:15:48
 * To change this template use File | Settings | File Templates.
 */
public class FileListTableModel extends AbstractTableModel {

    private List<Integer> numbers;
    private Map<Integer, String> numToFileNames;
    private Map<String, File> fileNamesToLocalFiles;

    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();

    public FileListTableModel() {
        numbers = new ArrayList<Integer>();         
        numToFileNames = new HashMap<Integer, String>();
        fileNamesToLocalFiles = new HashMap<String, File>();

    }

    public FileListTableModel(List<Integer> numbers, Map<Integer, String> numToFileNames) {
        this.numbers = numbers;
        this.numToFileNames = numToFileNames;
        fileNamesToLocalFiles = Collections.emptyMap();
    }

    @Override
    public int getRowCount() {

        return numbers.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Number";
            case 1:
                return "File Name";
            case 2:
                return "Local file";
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Integer keyNumber = numbers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return keyNumber;
            case 1:
                return getFileNameForNumber(keyNumber);
            case 2:
                return getLocalFileForNumber(keyNumber);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String getFileNameForNumber(int number) {
        if (numToFileNames.containsKey(number)) {
            return numToFileNames.get(number);
        } else {
            return "";
        }
    }

    private String getLocalFileForNumber(int number) {
       if (getFileNameForNumber(number).equals("")) {
           return "";
       } else if (fileNamesToLocalFiles.containsKey(getFileNameForNumber(number))) {
           return fileNamesToLocalFiles.get(getFileNameForNumber(number)).getAbsolutePath();
       } else {
           return "";
       }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
        fireTableStructureChanged();
    }

    public Map<Integer, String> getNumToFileNames() {
        return numToFileNames;
    }

    public void setNumToFileNames(Map<Integer, String> numToFileNames) {
        this.numToFileNames = numToFileNames;
        fireTableStructureChanged();
    }

    public Map<String, File> getFileNamesToLocalFiles() {
        return fileNamesToLocalFiles;
    }

    public void setFileNamesToLocalFiles(Map<String, File> fileNamesToLocalFiles) {
        this.fileNamesToLocalFiles = fileNamesToLocalFiles;
        fireTableStructureChanged();
    }
}
