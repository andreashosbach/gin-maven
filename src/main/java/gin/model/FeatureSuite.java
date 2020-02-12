package gin.model;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeatureSuite {
    private List<FeatureFile> featureFiles = new ArrayList<>();
    private String applicationName;
    private String applicationVersion;
    private String basePath;

    public static FeatureSuite fromPath(String featureFilePath) throws IOException {
        FeatureSuite featureSuite = new FeatureSuite();
        featureSuite.basePath = featureFilePath;

        List<File> featureFiles = listAllFeatureFilesInPath(featureFilePath);
        for (File file : featureFiles) {
            featureSuite.featureFiles.add(FeatureFile.fromFile(file, featureFilePath));
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

    public void setApplicationName(String applicationName){
        this.applicationName = applicationName;
    }

    public String getApplicationName(){
        return applicationName;
    }

    public void setApplicationVersion(String applicationVersion){
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationVersion(){
        return applicationVersion;
    }

    public List<FeatureFile> getFeatureFiles(){
        return ImmutableList.copyOf(featureFiles);
    }
}
