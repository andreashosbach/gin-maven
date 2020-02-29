package gin.scenarioo;

import gin.OutputFormatGenerator;
import gin.model.Step;
import gin.model.*;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.api.exception.ScenarioDocuSaveException;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ScenariooOutputGenerator implements OutputFormatGenerator {
    private FeatureSuite featureSuite;
    private ScenarioDocuWriter writer;

    ScenariooOutputGenerator(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public void writeOutput(String outputDirectory) throws IOException, URISyntaxException {
        if (Files.notExists(Paths.get(outputDirectory))) {
            Files.createDirectory(Paths.get(outputDirectory));
        }
        writer = new ScenarioDocuWriter(new File(outputDirectory), featureSuite.getApplicationName(), featureSuite.getApplicationVersion());

        featureSuite.getFeatures().forEach(this::mapFeature);
        try {
            writer.flush();
        } catch (ScenarioDocuSaveException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            Arrays.asList(e.getSuppressed()).forEach(System.out::println);
        }
    }

    private void mapFeature(Feature gFeature) {
        UseCase sUseCase = new UseCase();
        sUseCase.setName(gFeature.getName());
        sUseCase.setDescription(gFeature.getDescription());
        gFeature.getTags().forEach(sUseCase::addLabel);
        sUseCase.setStatus(convertResult(gFeature.getResult()));
        writer.saveUseCase(sUseCase);
        gFeature.getScenarios().forEach(s -> mapFeatureElement(sUseCase, s));
    }

    private void mapFeatureElement(UseCase sUseCase, FeatureElement featureElement) {
        Scenario sScenario = new Scenario();
        sScenario.setName(featureElement.getName());
        sScenario.setDescription(featureElement.getDescription());
        featureElement.getTags().forEach(sScenario::addLabel);
        sScenario.setStatus(convertResult(featureElement.getResult()));
        writer.saveScenario(sUseCase, sScenario);
        featureElement.getSteps().forEach(s -> mapStep(sUseCase, sScenario, s));
    }

    private void mapStep(UseCase sUseCase, Scenario sScenario, Step gStep) {
        org.scenarioo.model.docu.entities.Step sStep = new org.scenarioo.model.docu.entities.Step();
        sStep.setHtml(new StepHtml(createStepHtml(gStep)));
        StepDescription sStepDescription = new StepDescription();
        sStepDescription.setTitle(gStep.getKeyword().trim() + " " + gStep.getText());
        sStep.setStepDescription(sStepDescription);
        writer.saveStep(sUseCase, sScenario, sStep);
    }

    private String createStepHtml(Step gStep) {
        return "<b>" + gStep.getKeyword() + "</b>" + gStep.getText();
    }

    private Status convertResult(Result result) {
        if (result == Result.PASSED) {
            return Status.SUCCESS;
        }
        return Status.FAILED;
    }


}
