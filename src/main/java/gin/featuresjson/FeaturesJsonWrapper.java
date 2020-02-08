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
    @JsonIgnore
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public List<FeatureFile> Features;
    public Summary Summary;
    public Configuration Configuration;
    @JsonIgnore
    private String basePath;

    private static List<File> listAllFeatureFilesInPath(String directoryName) {
        logger.info("Path: " + directoryName);
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<>();

        if (!directory.isDirectory()) {
            resultList.add(directory);
        } else {
            File[] fileList = directory.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile() && file.getName().endsWith(".feature")) {
                        resultList.add(file);
                    } else if (file.isDirectory()) {
                        resultList.addAll(listAllFeatureFilesInPath(file.getAbsolutePath()));
                    }
                }
            } else {
                logger.info("Empty directory: " + directoryName);
            }
        }
        return resultList;
    }

    public static FeaturesJsonWrapper fromPath(String featureFilePath) throws IOException {
        FeaturesJsonWrapper jsonWrapper = new FeaturesJsonWrapper();
        jsonWrapper.Features = new ArrayList<>();
        jsonWrapper.basePath = featureFilePath;

        List<File> featureFiles = listAllFeatureFilesInPath(featureFilePath);
        for (File file : featureFiles) {
            jsonWrapper.Features.add(FeatureFile.fromFile(file, featureFilePath));
        }

        jsonWrapper.Configuration = gin.featuresjson.Configuration.create("GIN", "1.0");

        jsonWrapper.Summary = new Summary();
        jsonWrapper.Summary.Features = ResultSummary.create(featureFiles.size(), 0, 0, 0);
        jsonWrapper.Summary.Folders = new ArrayList<>();
        jsonWrapper.Summary.NotTestedFolders = new ArrayList<>();
        jsonWrapper.Summary.Scenarios = new ResultSummary();
        jsonWrapper.Summary.Tags = new ArrayList<>();

        return jsonWrapper;
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
}

