package gin.cucumberjson;

import gin.featuresjson.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestResultIntegrator {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    FeaturesJsonWrapper featuresJsonWrapper;

    public TestResultIntegrator(FeaturesJsonWrapper pWrapper) {
        this.featuresJsonWrapper = pWrapper;
    }

    public void integrateFromCucumberJson(CucumberJsonWrapper cucumberJson) {
        for (FeatureFile feature : featuresJsonWrapper.Features) {
            TestFile fileResult = findFileResult(cucumberJson, feature);
            integrateFeatureResults(fileResult, feature);
            feature.Result.WasProvided = feature.Feature.Result.WasProvided;
            feature.Result.WasSuccessful = feature.Feature.Result.WasSuccessful;
            feature.Result.WasExecuted = feature.Feature.Result.WasExecuted;
        }
    }

    private void integrateFeatureResults(TestFile fileResult, FeatureFile feature) {
        if (fileResult == null) {
            feature.Feature.Result.WasProvided = false;
            return;
        }

        feature.Feature.Result.WasProvided = true;
        feature.Feature.Result.WasSuccessful = true;
        feature.Feature.Result.WasExecuted = false;

        if (feature.Feature.Background != null) {
            TestCase caseResult = findBackgroundResult(fileResult, feature.Feature.Background);
            integrateBackgroundResults(caseResult, feature.Feature.Background);
            feature.Feature.Result.WasSuccessful &= feature.Feature.Background.Result.WasSuccessful;
            feature.Feature.Result.WasExecuted |= feature.Feature.Result.WasSuccessful;
        }

        for (Scenario scenario : feature.Feature.FeatureElements) {
            if (scenario.Examples != null) {
                List<TestCase> caseResults = findExamplesCaseResult(fileResult, scenario);
                integrateScenarioOutlineResults(caseResults, scenario);
            } else {
                TestCase caseResult = findCaseResult(fileResult, scenario);
                integrateScenarioResults(caseResult, scenario);
            }
            feature.Feature.Result.WasSuccessful &= scenario.Result.WasSuccessful;
            feature.Feature.Result.WasExecuted |= scenario.Result.WasExecuted;
        }
    }

    private TestFile findFileResult(CucumberJsonWrapper cucumberJson, FeatureFile feature) {
        for (TestFile testFile : cucumberJson.featuresResults) {
            if (samePath(feature.filePath, testFile.uri)) {
                return testFile;
            }
        }
        logger.warning("No result found for feature " + feature.filePath + " in");
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

        Path featureFilePath = Paths.get(featuresJsonWrapper.getBasePath()).relativize(Paths.get(featureFile));
        Path testPath = Paths.get(testUri);
        return testPath.endsWith(featureFilePath);
    }

    private TestCase findBackgroundResult(TestFile fileResult, Background background) {
        for (TestCase testCase : fileResult.elements) {
            if (testCase.line == background.line) {
                // TODO check if testCase is really a Background
                return testCase;
            }
        }
        logger.warning("No result found for background");
        return null;
    }

    private TestCase findCaseResult(TestFile fileResult, Scenario scenario) {
        for (TestCase testCase : fileResult.elements) {
            if (testCase.line == scenario.line && testCase.name.equals(scenario.Name)) {
                return testCase;
            }
        }
        logger.warning("No result found for scenario " + scenario.Name);
        return null;
    }

    private List<TestCase> findExamplesCaseResult(TestFile fileResult, Scenario scenario) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestCase testCase : fileResult.elements) {
            for (Example example : scenario.Examples) {
                for (Integer line : example.TableArgument.DataRowLines) {
                    if (testCase.line == line) {
                        testCases.add(testCase);
                    }
                }
            }
        }
        return testCases;
    }

    private void integrateBackgroundResults(TestCase caseResult, Background background) {
        integrateTestCaseResults(caseResult, background.Result);
    }

    private void integrateScenarioResults(TestCase caseResult, Scenario scenario) {
        integrateTestCaseResults(caseResult, scenario.Result);
    }

    private void integrateTestCaseResults(TestCase caseResult, Result result) {
        if (caseResult == null) {
            result.WasProvided = false;
        } else {
            result.WasProvided = true;
            result.WasExecuted = caseResult.isExecuted();
            result.WasSuccessful = caseResult.isSuccessful();
        }
    }

    private void integrateScenarioOutlineResults(List<TestCase> caseResult, Scenario scenario) {
        if (caseResult.isEmpty()) {
            scenario.Result.WasProvided = false;
            return;
        }
        scenario.Result.WasProvided = true;

        scenario.Result.WasSuccessful = true;
        for (TestCase testCase : caseResult) {
            scenario.Result.WasExecuted |= testCase.isExecuted();
            scenario.Result.WasSuccessful &= testCase.isSuccessful();
        }
    }
}
