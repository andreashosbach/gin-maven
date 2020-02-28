package gin.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeatureSuite {
    private List<Feature> features = new ArrayList<>();
    private String applicationName;
    private String applicationVersion;
    private String basePath;

    public static FeatureSuite fromPath(String featureFilePath) throws IOException {
        FeatureSuite featureSuite = new FeatureSuite();
        featureSuite.basePath = featureFilePath;

        List<File> featureFiles = listAllFeatureFilesInPath(featureFilePath);
        for (File file : featureFiles) {
            featureSuite.features.add(FeatureFile.fromFile(file, featureFilePath));
        }

        return featureSuite;
    }

    private static List<File> listAllFeatureFilesInPath(String directoryName) {
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
            }
        }
        return resultList;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public Feature getFeature(String name) {
        return features.stream().filter(f -> name.equals(f.getName())).findFirst().get();
    }
}
