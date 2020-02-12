package gin.model;

import io.cucumber.messages.Messages;

public class Scenario extends FeatureElement {
    public static Scenario fromGherkin(Messages.GherkinDocument.Feature.Scenario scenario) {
        Scenario pScenario = new Scenario();
        pScenario.setName(scenario.getName());
        pScenario.setDescription(scenario.getDescription());
        scenario.getStepsList().stream().forEachOrdered(s -> pScenario.addStep(Step.fromGherkin(s)));
        scenario.getTagsList().stream().forEachOrdered(t -> pScenario.addTag(t.getName()));

        pScenario.setLocation(Location.fromGherkin(scenario.getLocation()));

        return pScenario;
    }
}
