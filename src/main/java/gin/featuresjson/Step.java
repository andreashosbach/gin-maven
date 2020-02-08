package gin.featuresjson;

import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Step {
    public String Keyword;
    public String NativeKeyword;
    public String Name;
    public List<String> StepComments;
    public List<String> AfterLastStepComments;

    public static Step fromGherkin(Messages.GherkinDocument.Feature.Step step) {
        Step pStep = new Step();
        pStep.Keyword = KeywordTranslator.toStandard(step.getKeyword());
        pStep.NativeKeyword = step.getKeyword();
        pStep.StepComments = new ArrayList<>();
        pStep.AfterLastStepComments = new ArrayList<>();
        pStep.Name = step.getText();
        return pStep;
    }
}
