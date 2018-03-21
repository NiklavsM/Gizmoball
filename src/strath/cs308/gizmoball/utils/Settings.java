package strath.cs308.gizmoball.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final String TAG = "Settings";
    public static final File PATH = new File(System.getProperty("user.home") + "/Documents/Gizmoball/");
    public static final File SETTINGS_FILE = new File(PATH, "settings.xml");
    private static final Properties settingsProperties;


    static {
        settingsProperties = new Properties();
        reloadSettings();

        if (!PATH.exists()) {
            PATH.mkdirs();
        }

        if (!SETTINGS_FILE.exists()) {
            try {

                settingsProperties.setProperty("language", "en");
                settingsProperties.setProperty("3dEnabled", "false");

                settingsProperties.storeToXML(new FileOutputStream(SETTINGS_FILE), "");


                if (SETTINGS_FILE.createNewFile()) {
                    Logger.debug(TAG, "New settings file successfuly created");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String property) {
        return settingsProperties.getProperty("language");
    }

    public static void reloadSettings() {
        try {
            // Save
            settingsProperties.loadFromXML(new FileInputStream(Settings.SETTINGS_FILE));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSettings() {
        try {
            // Save
            settingsProperties.storeToXML(new FileOutputStream(SETTINGS_FILE), "");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
