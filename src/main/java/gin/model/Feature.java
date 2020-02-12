package gin.model;

import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private String name;
    private String description;
    private List<String> tags = new ArrayList<>();
    private Background background;
    private List<FeatureElement> scenarios = new ArrayList<>();

    public static Feature fromGherkin(Messages.GherkinDocument.Feature feature) {
        Feature pFeature = new Feature();
        pFeature.name = feature.getName();
        pFeature.description = feature.getDescription();
        feature.getChildrenList().stream().forEachOrdered(c -> pFeature.evaluateFeatureElement(pFeature, c));
        feature.getTagsList().stream().forEachOrdered(t -> pFeature.tags.add(t.getName()));
        return pFeature;
    }

    private void evaluateFeatureElement(Feature pFeature, Messages.GherkinDocument.Feature.FeatureChild c) {
        if (c.hasBackground()) {
            pFeature.background = Background.fromGherkin(c.getBackground());
        } else if (c.hasScenario()) {
            pFeature.scenarios.add(Scenario.fromGherkin(c.getScenario()));
        }
    }
}
