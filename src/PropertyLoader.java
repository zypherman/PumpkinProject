import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyLoader {
    Map<String, Integer> params = new HashMap<String, Integer>();
    InputStream inputStream;
    String propFileName;
    private static PropertyLoader instance = null;
    static String configFile = "config.properties";

    private PropertyLoader() {

        this.propFileName = configFile;
        try {
            loadPropertyFile();
        } catch (Exception e) {
            System.out.println("File Not Found");
        }
    }

    public static PropertyLoader getInstance() {
        if (instance == null) {
            instance = new PropertyLoader();
        }
        return instance;
    }

    public void loadPropertyFile() throws IOException {

        try {
            Properties prop = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            //Add each of these properties to a hash map
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                params.put(key, Integer.valueOf(value));
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close(); //If its null were screwed anyways
        }
    }

    public Integer getValue(String key) {
        return params.get(key);
    }


}
