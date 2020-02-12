package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturesJsonWrapper {
    public List<FeatureFile> Features;
    public Summary Summary;
    public Configuration Configuration;
    @JsonIgnore
    public String basePath;

    public String asFeaturesJson() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            return "jsonFeaturesWrapper(" + json + ");";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getBasePath() {
        return basePath;
    }
}

