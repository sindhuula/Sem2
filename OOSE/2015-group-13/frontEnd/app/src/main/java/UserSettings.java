/**
 * This is a data wrapper class that mirrors the server's UserSettings data object.  This
 * exists because the server will return this data in the form of JSon, and the LoginActivity
 * will use a transformer to create this data object for use in the rest of the app.
 */

public class UserSettings {

    public String defaultBackgroundImate;
    public Boolean soundSettings;
    public int wins;
    public int losses;
    public String displayName;

}
