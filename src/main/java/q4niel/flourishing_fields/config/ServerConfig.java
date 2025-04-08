package q4niel.flourishing_fields.config;

import com.google.gson.JsonSyntaxException;
import q4niel.flourishing_fields.FlourishingFields;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class ServerConfig {
    public ServerConfig(String configPath) {
        ServerConfigFile file = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(configPath))) {
            Gson gson = new Gson();

            String data = "";
            String line;
            while ((line = reader.readLine()) != null) {
                data += line;
            }

            try {
                file = gson.fromJson(data, ServerConfigFile.class);
            }
            catch (JsonSyntaxException e) {
                FlourishingFields.LOGGER.info("'flourishing_fields.json' SyntaxException: " + e.getMessage());
            }
        }
        catch (IOException e) {
            FlourishingFields.LOGGER.info("'flourishing_fields.json' IOException: " + e.getMessage());
        }

        if (file == null) {
            file = new ServerConfigFile (
                    5
            );
        }

        this.BEE_SPREAD_CHANCE = Math.max(0, Math.min(100, file.beeSpreadChance));
    }

    public final int BEE_SPREAD_CHANCE;
}