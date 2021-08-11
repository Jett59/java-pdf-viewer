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

    public String getString(String key) {
        return preferences.get(key, "");
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean isPresent(String key) {
        return preferences.get(key, null) != null;
    }
}
