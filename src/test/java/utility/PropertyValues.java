package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Pawel on 2017-07-25.
 */

public class PropertyValues {
    String result = "";
    InputStream inputStream;

    public String getPropValue(String propertyName) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Plik konfiguracyjny '" + propFileName + "' nie zostal znaleziony w podanej sciezce");
            }

            result = prop.getProperty(propertyName);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

}
