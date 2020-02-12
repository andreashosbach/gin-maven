package gin.cucumberjson;

import gin.model.*;

import javax.print.DocFlavor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ModelTestResultIntegrator {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    FeatureSuite featureSuite;

    public ModelTestResultIntegrator(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public void integrateFromCucumberJson(CucumberJsonWrapper cucumberJson) {
        for (FeatureFile feature : featureSuite.getFeatureFiles()) {
            TestFile fileResult = findFileResult(cucumberJson, feature);
            integrateFeatureResults(fileResult, feature);
        }
    }

    private void integrateFeatureResults(TestFile fileResult, FeatureFile feature) {
        if (fileResult == null) {
            return;
        }

        if (feature.getFeature().getBackground() != null) {
            TestCase caseResult = findBackgroundResult(fileResult, feature.getFeature().getBackground());
            integrateBackgroundResults(caseResult, feature.getFeature().getBackground());
        }

        for (FeatureElement featureElement : feature.getFeature().getScenarios()) {
            if (featureElement instanceof ScenarioOutline) {
                ScenarioOutline scenarioOutline = (ScenarioOutline) featureElement;
                List<TestCase> caseResults = findExamplesCaseResult(fileResult, scenarioOutline);
                integrateScenarioOutlineResults(caseResults, scenarioOutline);
            } else {
                Scenario scenario = (Scenario) featureElement;
                TestCase caseResult = findCaseResult(fileResult, scenario);
                integrateScenarioResults(caseResult, scenario);
            }
        }
    }

    private TestFile findFileResult(CucumberJsonWrapper cucumberJson, FeatureFile feature) {
        for (TestFile testFile : cucumberJson.featuresResults) {
            if (samePath(feature.getFilePath(), testFile.uri)) {
                return testFile;
            }
        }
        logger.warning("No result found for feature " + feature.getFilePath() + " in");
        for (TestFile testFile : cucumberJson.featuresResults) {
            logger.fine(testFile.uri);
        }
        return null;
    }

    private boolean samePath(String featureFile, String testUri) {
        //Clean path
        if (featureFile.startsWith("file:")) {
            featureFile = featureFile.substring(5);
        }
        if (testUri.startsWith("file:")) {
            testUri = testUri.substring(5);
        }

        Path featureFilePath = Paths.get(featureSuite.getBasePath()).relativize(Paths.get(featureFile));
        Path testPath = Paths.get(testUri);
        return testPath.endsWith(featureFilePath);
    }

    private TestCase findBackgroundResult(TestFile fileResult, Background background) {
        for (TestCase testCase : fileResult.elements) {
            if (testCase.line == background.getLocation().getLine()) {
                // TODO check if testCase is really a Background
                return testCase;
            }
        }
        logger.warning("No result found for background");
        return null;
    }

    private TestCase findCaseResult(TestFile fileResult, Scenario scenario) {
        for (TestCase testCase : fileResult.elements) {
            if (testCase.line == scenario.getLocation().getLine() && testCase.name.equals(scenario.getName())) {
                return testCase;
            }
        }
        logger.warning("No result found for scenario " + scenario.getName());
        return null;
    }

    private List<TestCase> findExamplesCaseResult(TestFile fileResult, ScenarioOutline scenario) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestCase testCase : fileResult.elements) {
            for (Example example : scenario.getExamples()) {
                for (Integer line : example.getDataRowLines()) {
                    if (testCase.line == line) {
                        testCases.add(testCase);
                    }
                }
            }
        }
        return testCases;
    }

    private void integrateBackgroundResults(TestCase caseResult, Background background) {
        background.setResult(convertResult(caseResult));
    }

    private void integrateScenarioResults(TestCase caseResult, Scenario scenario) {
        scenario.setResult(convertResult(caseResult));
    }

    private void integrateScenarioOutlineResults(List<TestCase> caseResult, ScenarioOutline scenarioOutline) {
        scenarioOutline.setResult(convertResults(caseResult));
    }

    private Result convertResult(TestCase caseResult) {
        if (caseResult == null || !caseResult.isExecuted()) {
            return Result.IGNORED;
        }
        if (caseResult.isSuccessful()) {
            return Result.PASSED;
        }
        return Result.FAILED;
    }

    private Result convertResults(List<TestCase> caseResult) {
        if (caseResult.isEmpty()) {
            return Result.IGNORED;
        }

        boolean executed = false;
        boolean successful = true;
        for (TestCase testCase : caseResult) {
            executed |= testCase.isExecuted();
            successful &= testCase.isSuccessful();
        }

        if(!executed) {
            return Result.IGNORED;
        }
        if(!successful) {
            return Result.FAILED;
        }
        return Result.PASSED;
    }
}
