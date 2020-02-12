package gin.model;

import com.google.common.collect.ImmutableList;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private String name;
    private String description;
    private String language;
    private List<String> tags = new ArrayList<>();
    private Background background;
    private List<FeatureElement> scenarios = new ArrayList<>();

    public static Feature fromGherkin(Messages.GherkinDocument.Feature feature) {
        Feature pFeature = new Feature();
        pFeature.name = feature.getName();
        pFeature.description = feature.getDescription();
        pFeature.language = feature.getLanguage();
        feature.getChildrenList().stream().forEachOrdered(c -> pFeature.evaluateFeatureElement(pFeature, c));
        feature.getTagsList().stream().forEachOrdered(t -> pFeature.tags.add(t.getName()));
        return pFeature;
    }

    private void evaluateFeatureElement(Feature pFeature, Messages.GherkinDocument.Feature.FeatureChild c) {
        if (c.hasBackground()) {
            pFeature.background = Background.fromGherkin(c.getBackground());
        } else if (c.hasScenario()) {
            if(c.getScenario().getExamplesList().size() > 0){
                pFeature.scenarios.add(ScenarioOutline.fromGherkin(c.getScenario()));
            }else {
                pFeature.scenarios.add(Scenario.fromGherkin(c.getScenario()));
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Background getBackground() {
        return background;
    }

    public List<String> getTags(){
        return ImmutableList.copyOf(tags);
    }

    public List<FeatureElement> getScenarios(){
        return ImmutableList.copyOf(scenarios);
    }

    public String getLanguage() {
        return language;
    }
}
