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
    public String Name;
    public String Description;
    public List<Scenario> FeatureElements;
    public Background Background;
    public List<String> Tags;
    public Result Result;

    public Scenario getScenario(String name) {
        for (Scenario s : FeatureElements) {
            if (s.Name.equals(name)) {
                return s;
            }
        }
        return null;
    }
}
