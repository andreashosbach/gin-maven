package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.TestResultIntegrator;
import gin.featuresjson.FeaturesJsonWrapper;
import gin.featuresjson.TestResultSummarizer;

import java.io.File;
import java.nio.file.Paths;


public class GinCLI {

    public static void main(String[] arg) throws Exception {
        if (arg.length != 3) {
            System.out.println("Usage GinCLI [featureFilePath] [testResultFile] [outputDirectory]");
            return;
        }

        String featureFiles = arg[0];
        String testResultFile = arg[1];
        String outputDirectory = arg[2];

        FeaturesJsonWrapper fWrapper = FeaturesJsonWrapper.fromPath(featureFiles);

        CucumberJsonWrapper testResult = CucumberJsonWrapper.fromFile(testResultFile);
        TestResultIntegrator testResultIntegrator = new TestResultIntegrator(fWrapper);
        testResultIntegrator.integrateFromCucumberJson(testResult);

        TestResultSummarizer summarizer = new TestResultSummarizer(fWrapper);
        summarizer.summarize();

        String pickledJson = fWrapper.asFeaturesJson();

        FileUtils.copyFromJar("html", Paths.get(outputDirectory));
        FileUtils.saveToFile(pickledJson, outputDirectory + File.separator + "html" + File.separator + "features.js");
    }
}
