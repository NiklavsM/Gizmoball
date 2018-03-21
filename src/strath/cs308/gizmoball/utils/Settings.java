package strath.cs308.gizmoball.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    public static final File PATH = new File(System.getProperty("user.home") + "/Documents/Gizmoball/");
    public static final File SETTINGS_FILE = new File(PATH, "settings.xml");
    private static final String TAG = "Settings";
    private static final Properties settingsProperties;


    static {
        settingsProperties = new Properties();

        if (!PATH.exists()) {
            PATH.mkdirs();
        }

        if (!SETTINGS_FILE.exists()) {
            try {

                settingsProperties.setProperty("language", "en");
                settingsProperties.setProperty("3dEnabled", "false");
                settingsProperties.setProperty("shadowsEnabled", "true");

                settingsProperties.storeToXML(new FileOutputStream(SETTINGS_FILE), "");

                if (SETTINGS_FILE.createNewFile()) {
                    Logger.debug(TAG, "New settings file successfuly created");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        reloadSettings();
        Logger.debug(TAG, "Language: " + settingsProperties.getProperty("language"));
        Logger.debug(TAG, "3dEnabled: " + settingsProperties.getProperty("3dEnabled"));
        Logger.debug(TAG, "shadowsEnabled: " + settingsProperties.getProperty("shadowsEnabled"));

    }

    public static String getProperty(String property) {
        return settingsProperties.getProperty(property);
    }

    public static void setProperty(String key, String value) {
        settingsProperties.setProperty(key, value);

        Logger.debug(TAG, "Property: " + key + " set to : " + value);

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
