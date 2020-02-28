package gin.html;

import gin.OutputFormatFactory;
import gin.model.FeatureSuite;

public class HtmlFactory implements OutputFormatFactory {
    private FeatureSuite featureSuite;

    public HtmlFactory(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public HtmlOutputGenerator buildOutputGenerator() {
        return new HtmlOutputGenerator(featureSuite);
    }
}
