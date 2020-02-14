package gin.model;

import gin.featuresjson.KeywordTranslator;
import io.cucumber.messages.Messages;

public class Step {
    public String keyword;
    public String nativeKeyword;
    public String text;
    private Result result;
    private Location location;

    public static Step fromGherkin(Messages.GherkinDocument.Feature.Step step) {
        Step pStep = new Step();
        pStep.keyword = KeywordTranslator.toStandard(step.getKeyword());
        pStep.nativeKeyword = step.getKeyword();
        pStep.text = step.getText();
        pStep.location = Location.fromGherkin(step.getLocation());
        return pStep;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getText() {
        return text;
    }

    public Location getLocation() {
        return location;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult(){
        return result;
    }
}
