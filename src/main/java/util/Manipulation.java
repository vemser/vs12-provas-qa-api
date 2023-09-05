package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Manipulation {

    private Manipulation() {}

    public static Properties getProp() {
        Properties props = new Properties();
            try {
                FileInputStream file = new FileInputStream("/C:/DBC/workspace/vs12-qa/modulo-04-api/restassured/src/main/resources/config.properties");
                props.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return props;
    }
}
