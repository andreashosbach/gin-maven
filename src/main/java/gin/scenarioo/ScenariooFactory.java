package gin.scenarioo;

import gin.OutputFormatFactory;
import gin.model.FeatureSuite;

public class ScenariooFactory implements OutputFormatFactory {
    private FeatureSuite featureSuite;

    public ScenariooFactory(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public ScenariooOutputGenerator buildOutputGenerator() {
        return new ScenariooOutputGenerator(featureSuite);
    }
}
