package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.CucumberTestResultIntegrator;
import gin.featuresjson.FeaturesJsonFactory;
import gin.featuresjson.FeaturesJsonOutputGenerator;
import gin.model.FeatureSuite;


public class GinCLI {

    public static void main(String[] arg) throws Exception {
        if (arg.length != 3) {
            System.out.println("Usage GinCLI [featureFilePath] [testResultFile] [outputDirectory]");
            return;
        }

        String featureFiles = arg[0];
        String testResultFile = arg[1];
        String outputDirectory = arg[2];

        FeatureSuite featureSuite = FeatureSuite.fromPath(featureFiles);

        CucumberJsonWrapper testResult = CucumberJsonWrapper.fromFile(testResultFile);
        new CucumberTestResultIntegrator(featureSuite).integrateFromCucumberJson(testResult);

        FeaturesJsonFactory featuresJsonFactory = new FeaturesJsonFactory(featureSuite);
        FeaturesJsonOutputGenerator fWrapper = featuresJsonFactory.buildOutputGenerator();
        fWrapper.writeOutput(outputDirectory);
    }
}
