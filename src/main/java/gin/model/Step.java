package gin.model;

import gin.featuresjson.KeywordTranslator;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Step {
    public String keyword;
    public String nativeKeyword;
    public String text;
    private Result result;

    public static Step fromGherkin(Messages.GherkinDocument.Feature.Step step) {
        Step pStep = new Step();
        pStep.keyword = KeywordTranslator.toStandard(step.getKeyword());
        pStep.nativeKeyword = step.getKeyword();
        pStep.text = step.getText();
        return pStep;
    }
}
