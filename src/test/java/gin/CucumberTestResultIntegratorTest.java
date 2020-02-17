package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.CucumberTestResultIntegrator;
import gin.model.*;
import org.junit.jupiter.api.Test;
import static gin.model.Result.PASSED;
import static gin.model.Result.FAILED;

import static org.junit.jupiter.api.Assertions.*;

public class CucumberTestResultIntegratorTest {
    private static void checkScenario(FeatureElement featureElement, Result result) {
        assertNotNull(featureElement);
        assertEquals(result, featureElement.getResult(), featureElement.getName() + " (" + result +")");
    }

    @Test
    public void integrateFromCucumberJson() throws Exception {
        //Given
        String featureDirectory = CucumberTestResultIntegratorTest.class.getResource("/featuresWithResults").getFile();
        featureDirectory = featureDirectory.replaceFirst("/", "");
        FeatureSuite featureSuite = FeatureSuite.fromPath(featureDirectory);
        String resultFile = CucumberTestResultIntegratorTest.class.getResource("/featuresWithResults/cucumber.json").getFile();

        resultFile = resultFile.replaceFirst("/", "");
        CucumberJsonWrapper cucumberJson = CucumberJsonWrapper.fromFile(resultFile);

        //When
        CucumberTestResultIntegrator integrator = new CucumberTestResultIntegrator(featureSuite);
        integrator.integrateFromCucumberJson(cucumberJson);

        //Then
        Feature feature = featureSuite.getFeature("Some Scenarios with results");
        checkScenario(feature.getScenario("Successful Scenario"), PASSED);
        checkScenario(feature.getScenario("Failed Scenario"), FAILED);
        checkScenario(feature.getScenario("Successful Scenario Outline"), PASSED);
        checkScenario(feature.getScenario("Failed Scenario Outline"), FAILED);
    }
}
