package q4niel.flourishing_fields.config;

public class ServerConfigFile {
    public ServerConfigFile (
            int beeSpreadChance,
            String[] flowerSpreadBlacklist
    ) {
        this.beeSpreadChance = beeSpreadChance;
        this.flowerSpreadBlacklist = flowerSpreadBlacklist;
    }

    public int beeSpreadChance;
    public String[] flowerSpreadBlacklist;
}