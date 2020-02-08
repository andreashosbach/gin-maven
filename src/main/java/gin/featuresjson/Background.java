package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Background {
    @JsonIgnore
    public int line;
    @JsonIgnore
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String Name;
    public String Description;
    public List<Step> Steps;
    public List<String> Tags;
    public Result Result;

    public static Background fromGherkin(Messages.GherkinDocument.Feature.Background background) {
        logger.fine("Background:" + background.getName() + " Steps: " + background.getStepsCount());

        Background pBackground = new Background();
        pBackground.Name = background.getName();
        pBackground.Description = background.getDescription();
        pBackground.Result = new Result();
        pBackground.Tags = new ArrayList<>();
        pBackground.Steps = new ArrayList<>();
        background.getStepsList().stream().forEachOrdered(s -> pBackground.Steps.add(Step.fromGherkin(s)));
        pBackground.line = background.getLocation().getLine();
        return pBackground;
    }
}
