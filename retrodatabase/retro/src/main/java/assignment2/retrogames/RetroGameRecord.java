package assignment2.retrogames;

public class RetroGameRecord {

    private DataKey key;
    private String about;
    private String sound;
    private String image;

    public RetroGameRecord() { this(null, null, null, null); }

    public RetroGameRecord(DataKey k, String a, String s, String i) {
        key = k;
        about = a;
        sound = s;
        image = i;
    }

    public DataKey getDataKey() { return key; }
    public String getAbout() { return about; }
    public String getSound() { return sound; }
    public String getImage() { return image; }
}
