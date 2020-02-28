package gin.html;

import gin.model.FeatureSuite;
import org.junit.jupiter.api.Test;

class HtmlOutputGeneratorTest {
    @Test
    public void testFromPath() throws Exception {
        // Given
        String featureDirectory = HtmlOutputGeneratorTest.class.getResource("/features").getFile();
        featureDirectory = featureDirectory.replaceFirst("/", "");
        FeatureSuite featureSuite = FeatureSuite.fromPath(featureDirectory);
        HtmlFactory htmlFactory = new HtmlFactory(featureSuite);

        // When
        HtmlOutputGenerator htmlOutputGenerator = htmlFactory.buildOutputGenerator();

        // Then
        //htmlOutputGenerator.writeOutput("confluence");
    }
}