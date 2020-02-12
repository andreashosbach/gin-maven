package gin.model;

import io.cucumber.messages.Messages;

public class Background extends FeatureElement{
    public static Background fromGherkin(Messages.GherkinDocument.Feature.Background background) {
        Background pBackground = new Background();
        pBackground.setName(background.getName());
        pBackground.setDescription(background.getDescription());
        background.getStepsList().stream().forEachOrdered(s -> pBackground.addStep(Step.fromGherkin(s)));
        pBackground.setLocation(Location.fromGherkin(background.getLocation()));
        return pBackground;
    }
}
