package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.TestResultIntegrator;
import gin.featuresjson.Feature;
import gin.featuresjson.FeatureFile;
import gin.featuresjson.FeaturesJsonWrapper;
import gin.featuresjson.Scenario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestResultIntegratorTest {
    private static void checkScenario(Feature feature, String name, boolean wasSuccessful, boolean wasExecuted, boolean wasProvided) {
        Scenario scenario = feature.getScenario(name);
        assertNotNull(scenario, name);
        assertEquals(wasSuccessful, scenario.Result.WasSuccessful, name + " (Success)");
        assertEquals(wasExecuted, scenario.Result.WasExecuted, name + "(Executed)");
        assertEquals(wasProvided, scenario.Result.WasProvided, name + "(Provided)");
    }

    @Test
    public void integrateFromCucumberJson() throws Exception {
        //Given
        String featureDirectory = TestResultIntegratorTest.class.getResource("/featuresWithResults").getFile();
        featureDirectory = featureDirectory.replaceFirst("/", "");

        FeaturesJsonWrapper featuresJson = FeaturesJsonWrapper.fromPath(featureDirectory);
        String resultFile = TestResultIntegratorTest.class.getResource("/featuresWithResults/cucumber.json").getFile();
        resultFile = resultFile.replaceFirst("/", "");
        CucumberJsonWrapper cucumberJson = CucumberJsonWrapper.fromFile(resultFile);

        //When
        TestResultIntegrator integrator = new TestResultIntegrator(featuresJson);
        integrator.integrateFromCucumberJson(cucumberJson);
        //  System.out.println(featuresJson.asFeaturesJson());
        FeatureFile featureFile = featuresJson.Features.get(0);

        //Then
        assertFalse(featureFile.Result.WasSuccessful, featureFile.RelativeFolder);
        assertTrue(featureFile.Result.WasProvided, featureFile.RelativeFolder);
        assertTrue(featureFile.Result.WasExecuted, featureFile.RelativeFolder);

        checkScenario(featureFile.Feature, "Successful Scenario", true, true, true);
        checkScenario(featureFile.Feature, "Failed Scenario", false, true, true);
        checkScenario(featureFile.Feature, "Successful Scenario Outline", true, true, true);
        checkScenario(featureFile.Feature, "Failed Scenario Outline", false, true, true);
    }
}
