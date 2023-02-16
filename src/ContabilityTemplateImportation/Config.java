package ContabilityTemplateImportation;

import java.io.File;

import org.ini4j.Ini;

public class Config {
    private static String configFileName = "./robot-protecaes-received.ini";
    public static Ini config = loadConfig();

    public static Ini loadConfig() {
        File file = new File(configFileName);
        try {
            config = new Ini(file);
        } catch (Exception e){
            throw new RuntimeException("Erro ao carregar arquivo de configuração: " + configFileName);
        }

        return config;
    }
}
