package gin.featuresjson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Configuration {
    public String SutName;
    public String SutVersion;
    public String GeneratedOn;

    public static Configuration create(String name, String version) {
        Configuration pConfiguration = new Configuration();
        pConfiguration.GeneratedOn = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        pConfiguration.SutName = name;
        pConfiguration.SutVersion = version;
        return pConfiguration;
    }
}
