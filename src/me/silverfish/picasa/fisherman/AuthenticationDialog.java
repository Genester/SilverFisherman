package me.silverfish.picasa.fisherman;

import javax.swing.*;
import java.awt.event.*;

public class AuthenticationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loginField;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private JPasswordField passwordField;

    private String username = null;
    private String password = null;

    public AuthenticationDialog() {
        setTitle("SilverFisherman");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setResizable(false);
    }

    private void onOK() {
        username = loginField.getText();
        password = new String(passwordField.getPassword());
        dispose();
    }

    private void onCancel() {
        username = null;
        password = null;
        dispose();
    }

    public static void main(String[] args) {
        AuthenticationDialog dialog = new AuthenticationDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
