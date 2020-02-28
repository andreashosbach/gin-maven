package gin.scenarioo;

import gin.OutputFormatGenerator;
import gin.model.FeatureSuite;

import java.io.IOException;
import java.net.URISyntaxException;

public class ScenariooOutputGenerator implements OutputFormatGenerator {
    private FeatureSuite featureSuite;

    ScenariooOutputGenerator(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public void writeOutput(String outputDirectory) throws IOException, URISyntaxException {
    }
}
