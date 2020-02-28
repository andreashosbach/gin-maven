package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gin.FileUtils;
import gin.OutputFormatGenerator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturesJsonOutputGenerator implements OutputFormatGenerator {
    public List<FeatureFile> Features;
    public Summary Summary;
    public Configuration Configuration;
    @JsonIgnore
    public String basePath;

    FeaturesJsonOutputGenerator() {
    }

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

    public void writeOutput(String outputDirectory) throws IOException, URISyntaxException {
        new TestResultSummarizer(this).summarize();

        String featuresJson = asFeaturesJson();
        FileUtils.copyFromJar("html", Paths.get(outputDirectory));
        FileUtils.saveToFile(featuresJson, outputDirectory + File.separator + "html" + File.separator + "features.js");
    }
}

