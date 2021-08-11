package app.cleancode.java_pdf_viewer.prefs;

import java.util.prefs.Preferences;

public class PreferenceManager {
    private static Preferences preferences =
            Preferences.userNodeForPackage(PreferenceManager.class);

    public static String LAST_DOCUMENT = "last_document";
    public static String LAST_PAGE_NUMBER = "last_page_number";

    public static void put(String key, String value) {
        preferences.put(key, value);
    }

    public static void put(String key, int value) {
        preferences.putInt(key, value);
    }

    public static String getString(String key) {
        return preferences.get(key, "");
    }

    public static int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static boolean isPresent(String key) {
        return preferences.get(key, null) != null;
    }
}
