package gin.model;

import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class ScenarioOutline extends FeatureElement {
    private List<Example> examples = new ArrayList<>();

    public static ScenarioOutline fromGherkin(Messages.GherkinDocument.Feature.Scenario scenario) {
        ScenarioOutline pScenario = new ScenarioOutline();
        pScenario.setName(scenario.getName());
        pScenario.setDescription(scenario.getDescription());
        scenario.getStepsList().stream().forEachOrdered(s -> pScenario.addStep(Step.fromGherkin(s)));
        scenario.getTagsList().stream().forEachOrdered(t -> pScenario.addTag(t.getName()));

        pScenario.setLocation(Location.fromGherkin(scenario.getLocation()));

        if (scenario.getExamplesCount() > 0) {
            scenario.getExamplesList().stream().forEachOrdered(x -> pScenario.examples.add(Example.fromGherkin(x)));
        }
        return pScenario;
    }

}
