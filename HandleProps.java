import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class HandleProps {
    public static void main( String[] args ) {
        Properties prop = new Properties();
        
        try {

            prop.setProperty("name","Mike");
            prop.setProperty("city", "Middletown");
            prop.setProperty("state", "NJ");
            prop.setProperty("day", "Wednesday");

            prop.store(new FileOutputStream("config.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
