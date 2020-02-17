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
}
