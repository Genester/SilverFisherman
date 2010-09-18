package me.silverfish.picasa.fisherman;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.util.ServiceException;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 15:51:21
 * To change this template use File | Settings | File Templates.
 */
public class Fisherman {

    public static String APP_NAME = "SilverFisherman";

    public static void main(String[] args) {
        PicasaClient client = null;
        while (true) {
            AuthenticationDialog authDialog = new AuthenticationDialog();
            authDialog.setVisible(true);
            if (authDialog.getPassword() == null ||
                authDialog.getUsername() == null) {
                System.exit(0);
            } else {
                try {
                    client = new PicasaClient(authDialog.getUsername(), authDialog.getPassword());
                    break;
                } catch (IllegalArgumentException e) {
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
                            "Логин или пароль неверен,\nпопробовать еще раз?", "Ошибка!",
                            JOptionPane.OK_CANCEL_OPTION)) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }

        try {
            for (AlbumEntry album : client.getAlbums()) {
                System.out.println(album.getTitle().getPlainText());
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            FishermanMain main = new FishermanMain(client);
            main.show();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
