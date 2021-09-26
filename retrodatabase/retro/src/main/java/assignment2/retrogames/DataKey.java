package assignment2.retrogames;

public class DataKey {
    private String gameName;
    private int gameType;

    public DataKey() { this( null, 0 ); }

    public DataKey(String name, int type) {
        gameName = name;
        gameType = type;
    }

    public String getGameName() { return gameName; }
    public int getGameType() { return gameType; }

    /**
     * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
     * than k, and it returns 1 otherwise.
     */
    public int compareTo(DataKey k) {
        if (this.getGameType() == k.getGameType()) {
            int compare = this.gameName.compareTo(k.getGameName());
            if (compare == 0){
                return 0;
            }
            else if (compare < 0) {
                return -1;
            }
        }
        else if(this.getGameType() < k.getGameType()){
            return -1;
        }
        return 1;

    }
}
