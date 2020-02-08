package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Gherkin: Feature (Together with FeatureFile)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    @JsonIgnore
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String Name;
    public String Description;
    public List<Scenario> FeatureElements;
    public Background Background;
    public List<String> Tags;
    public Result Result;

    public static Feature fromGherkin(Messages.GherkinDocument.Feature feature) {
        logger.fine("Feature: " + feature.getName() + " Children:" + feature.getChildrenCount());
        KeywordTranslator.setLanguage(feature.getLanguage());

        Feature pFeature = new Feature();
        pFeature.Name = feature.getName();
        pFeature.Description = feature.getDescription();
        pFeature.FeatureElements = new ArrayList<>();
        feature.getChildrenList().stream().forEachOrdered(c -> evaluateFeatureChild(pFeature, c));
        pFeature.Result = new Result();
        pFeature.Tags = new ArrayList<>();
        feature.getTagsList().stream().forEachOrdered(t -> pFeature.Tags.add(t.getName()));
        return pFeature;
    }

    private static void evaluateFeatureChild(Feature pFeature, Messages.GherkinDocument.Feature.FeatureChild c) {
        if (c.hasBackground()) {
            pFeature.Background = gin.featuresjson.Background.fromGherkin(c.getBackground());
        } else if (c.hasScenario()) {
            pFeature.FeatureElements.add(Scenario.fromGherkin(c.getScenario()));
        }
    }

    public Scenario getScenario(String name) {
        for (Scenario s : FeatureElements) {
            if (s.Name.equals(name)) {
                return s;
            }
        }
        return null;
    }
}
