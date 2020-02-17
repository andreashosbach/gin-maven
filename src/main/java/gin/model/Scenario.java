package gin.model;

import io.cucumber.messages.Messages;

public class Scenario extends FeatureElement {
    private Result result;

    public static Scenario fromGherkin(Messages.GherkinDocument.Feature.Scenario scenario) {
        Scenario pScenario = new Scenario();
        pScenario.setName(scenario.getName());
        pScenario.setDescription(scenario.getDescription());
        scenario.getStepsList().stream().forEachOrdered(s -> pScenario.addStep(Step.fromGherkin(s)));
        scenario.getTagsList().stream().forEachOrdered(t -> pScenario.addTag(t.getName()));

        pScenario.setLocation(Location.fromGherkin(scenario.getLocation()));

        return pScenario;
    }

    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return result;
    }
}
