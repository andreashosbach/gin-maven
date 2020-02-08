package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gherkin: Scenario
 */
public class Scenario {
    @JsonIgnore
    public int line;

    public String Name;
    public String Slug;
    public String Description;
    public List<Step> Steps;
    public List<String> Tags;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Example> Examples;
    public Result Result;


    public static Scenario fromGherkin(Messages.GherkinDocument.Feature.Scenario scenario) {
        Scenario pScenario = new Scenario();
        pScenario.Name = scenario.getName();
        pScenario.Description = scenario.getDescription();
        pScenario.Slug = UUID.randomUUID().toString();
        pScenario.Steps = new ArrayList<>();
        scenario.getStepsList().stream().forEachOrdered(s -> pScenario.Steps.add(Step.fromGherkin(s)));
        pScenario.Result = new Result();
        pScenario.Tags = new ArrayList<>();
        scenario.getTagsList().stream().forEachOrdered(t -> pScenario.Tags.add(t.getName()));

        pScenario.line = scenario.getLocation().getLine();

        if (scenario.getExamplesCount() > 0) {
            pScenario.Examples = new ArrayList<>();
            scenario.getExamplesList().stream().forEachOrdered(x -> pScenario.Examples.add(Example.fromGherkin(x)));
        }
        return pScenario;
    }
}
