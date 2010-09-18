package me.silverfish.picasa.fisherman;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 21:15:48
 * To change this template use File | Settings | File Templates.
 */
public class FileListTableModel implements TableModel {

    private List<Integer> numbers;
    private Map<Integer, String> numToFileNames;
    private Map<String, File> fileNamesToLocalFiles;

    public FileListTableModel(List<Integer> numbers, Map<Integer, String> numToFileNames) {
        this.numbers = numbers;
        this.numToFileNames = numToFileNames;
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
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Integer keyNumber = numbers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return keyNumber;
            case 1:
                return getFileNameForNumber
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String getFileNameForNumber(int number) {

    }

    private String getLocalFileForNumber(int number) {

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public Map<Integer, String> getNumToFileNames() {
        return numToFileNames;
    }

    public void setNumToFileNames(Map<Integer, String> numToFileNames) {
        this.numToFileNames = numToFileNames;
    }

    public Map<String, File> getFileNamesToLocalFiles() {
        return fileNamesToLocalFiles;
    }

    public void setFileNamesToLocalFiles(Map<String, File> fileNamesToLocalFiles) {
        this.fileNamesToLocalFiles = fileNamesToLocalFiles;
    }
}
