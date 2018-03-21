package strath.cs308.gizmoball.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final String TAG = "Settings";
    public static File PATH = new File(System.getProperty("user.home") + "/Documents/Gizmoball/");
    public static File SETTINGS_FILE = new File(PATH, "settings.xml");

    static {
        if(!PATH.exists()) {
            PATH.mkdirs();
        }

        if(!SETTINGS_FILE.exists()){
            try {

                Properties settings = new Properties();
                settings.setProperty("language", "en");
                settings.setProperty("3dEnabled", "false");

                settings.storeToXML(new FileOutputStream(SETTINGS_FILE), "");


                if (SETTINGS_FILE.createNewFile()) {
                    Logger.debug(TAG, "New settings file successfuly created");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
