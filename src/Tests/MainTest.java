package Tests;
import ContabilityTemplateImportation.Main;

public class MainTest {

    public static void main(String[] args) {
        StringBuilder parametros = new StringBuilder();
        
        parametros.append("[mes:5]");
        parametros.append("[ano:2021]");
        parametros.append("[ini:robot-protecaesCEFReceb]");

        Main.testParameters = parametros.toString();
        args = new String[]{"test"};

        Main.main(args);
    }
}
