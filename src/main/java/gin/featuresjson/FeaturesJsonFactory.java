package gin.featuresjson;

import gin.model.FeatureElement;
import gin.model.FeatureSuite;
import gin.model.ScenarioOutline;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static gin.model.Result.*;

public class FeaturesJsonFactory {
    private FeatureSuite featureSuite;

    public FeaturesJsonFactory(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public FeaturesJsonWrapper buildFeaturesJsonWrapper() {
        FeaturesJsonWrapper jsonWrapper = new FeaturesJsonWrapper();
        jsonWrapper.Features = new ArrayList<>();

        featureSuite.getFeatures().stream().forEachOrdered(f -> jsonWrapper.Features.add(createFeatureFile(f)));

        jsonWrapper.Configuration = gin.featuresjson.Configuration.create("GIN", "1.0");

        jsonWrapper.Summary = new Summary();
        jsonWrapper.Summary.Features = ResultSummary.create(jsonWrapper.Features.size(), 0, 0, 0);
        jsonWrapper.Summary.Folders = new ArrayList<>();
        jsonWrapper.Summary.NotTestedFolders = new ArrayList<>();
        jsonWrapper.Summary.Scenarios = new ResultSummary();
        jsonWrapper.Summary.Tags = new ArrayList<>();
        jsonWrapper.basePath = featureSuite.getBasePath();

        return jsonWrapper;
    }

    private FeatureFile createFeatureFile(gin.model.Feature feature) {
        FeatureFile pFeatures = new FeatureFile();
        pFeatures.filePath = feature.getFeatureFileName();
        pFeatures.RelativeFolder = Paths.get(featureSuite.getBasePath()).relativize(Paths.get(feature.getFeatureFileName())).toString();
        pFeatures.Feature = createFeature(feature);
        pFeatures.Result = createResult(feature.getResult());

        return pFeatures;
    }

    private Feature createFeature(gin.model.Feature feature) {
        KeywordTranslator.setLanguage(feature.getLanguage());

        Feature pFeature = new Feature();
        pFeature.Name = feature.getName();
        pFeature.Description = feature.getDescription();
        pFeature.FeatureElements = new ArrayList<>();
        if (feature.getBackground() != null) {
            pFeature.Background = createBackground(feature.getBackground());
        }
        feature.getScenarios().stream().forEachOrdered(c -> pFeature.FeatureElements.add(createScenario(c)));
        pFeature.Result = createResult(feature.getResult());
        pFeature.Tags = new ArrayList<>();
        feature.getTags().stream().forEachOrdered(t -> pFeature.Tags.add(t));
        return pFeature;
    }

    private Scenario createScenario(FeatureElement featureElement) {
        Scenario pScenario = new Scenario();
        pScenario.Name = featureElement.getName();
        pScenario.Description = featureElement.getDescription();
        pScenario.Slug = UUID.randomUUID().toString();
        pScenario.Steps = new ArrayList<>();
        featureElement.getSteps().stream().forEachOrdered(s -> pScenario.Steps.add(createStep(s)));
        pScenario.Result = createResult(featureElement.getResult());
        pScenario.Tags = new ArrayList<>();
        featureElement.getTags().stream().forEachOrdered(t -> pScenario.Tags.add(t));

        pScenario.line = featureElement.getLocation().getLine();

        if (featureElement instanceof ScenarioOutline) {
            ScenarioOutline scenarioOutline = (ScenarioOutline) featureElement;
            pScenario.Examples = new ArrayList<>();
            scenarioOutline.getExamples().stream().forEachOrdered(x -> pScenario.Examples.add(createExample(x)));
        }
        return pScenario;
    }

    private Background createBackground(gin.model.Background background) {
        Background pBackground = new Background();
        pBackground.Name = background.getName();
        pBackground.Description = background.getDescription();
        pBackground.Result = createResult(background.getResult());
        pBackground.Tags = new ArrayList<>();
        pBackground.Steps = new ArrayList<>();
        background.getSteps().stream().forEachOrdered(s -> pBackground.Steps.add(createStep(s)));
        pBackground.line = background.getLocation().getLine();
        return pBackground;
    }

    private Step createStep(gin.model.Step step) {
        Step pStep = new Step();
        pStep.Keyword = KeywordTranslator.toStandard(step.getKeyword());
        pStep.NativeKeyword = step.getKeyword();
        pStep.StepComments = new ArrayList<>();
        pStep.AfterLastStepComments = new ArrayList<>();
        pStep.Name = step.getText();
        return pStep;
    }

    public static Example createExample(gin.model.Example example) {
        Example pExample = new Example();
        pExample.Name = example.getName();
        pExample.TableArgument = new Example.TableArgument();
        pExample.Tags = new ArrayList<>();
        example.getTags().stream().forEachOrdered(t -> pExample.Tags.add(t));
        pExample.NativeKeyword = example.getKeyword().trim();

        pExample.TableArgument.HeaderRow = new ArrayList<>();

        example.getHeaders().stream().forEachOrdered(c -> pExample.TableArgument.HeaderRow.add(c));

        pExample.TableArgument.DataRows = new ArrayList<>();
        pExample.TableArgument.DataRowLines = new ArrayList<>();
        example.getDataRows().stream().forEachOrdered((row) ->
        {
            List<String> pRow = new ArrayList<>();
            row.stream().forEachOrdered(c -> pRow.add(c));
            pExample.TableArgument.DataRows.add(pRow);
        });
        example.getDataRowLines().stream().forEachOrdered(l -> pExample.TableArgument.DataRowLines.add(l));

        return pExample;
    }

    private Result createResult(gin.model.Result result) {
        Result pResult = new Result();
        if (result == null) {
            pResult.WasExecuted = false;
            pResult.WasProvided = false;
            pResult.WasSuccessful = false;
        } else {
            pResult.WasExecuted = (result != IGNORED) && (result != UNKNOWN);
            pResult.WasProvided = (result != UNKNOWN);
            pResult.WasSuccessful = (result == PASSED);
        }
        return pResult;
    }


}
