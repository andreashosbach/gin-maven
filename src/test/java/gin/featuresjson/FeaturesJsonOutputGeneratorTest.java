package gin.featuresjson;

import gin.model.FeatureSuite;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeaturesJsonOutputGeneratorTest {
    @Test
    public void testFromPath() throws IOException {
        // Given
        String featureDirectory = FeaturesJsonOutputGeneratorTest.class.getResource("/features").getFile();
        featureDirectory = featureDirectory.replaceFirst("/", "");
        FeatureSuite featureSuite = FeatureSuite.fromPath(featureDirectory);
        featureSuite.setApplicationName("GIN");
        featureSuite.setApplicationVersion("1.0");
        FeaturesJsonFactory featuresJsonFactory = new FeaturesJsonFactory(featureSuite);

        // When
        FeaturesJsonOutputGenerator featuresJson = featuresJsonFactory.buildOutputGenerator();

        // Then
        assertEquals(6, featuresJson.Features.size());
        String[] actualLines = featuresJson.asFeaturesJson().split("\n");
        String filename = FeaturesJsonOutputGeneratorTest.class.getResource("/features.js").getFile();
        filename = filename.replaceFirst("/", "");
        List<String> expectedLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            stream.forEach(expectedLines::add);
        }

        //assertEquals(expectedLines.size(), actualLines.length);
        for (int i = 0; i < expectedLines.size(); i++) {
            String expectedLine = expectedLines.get(i).trim();
            String actualLine = actualLines[i].trim();
            if ((!expectedLine.startsWith("\"Slug\"") && !actualLine.startsWith("\"Slug\"")) &&
                    (!expectedLine.startsWith("\"GeneratedOn\"") && !actualLine.startsWith("\"GeneratedOn\""))) {
                assertEquals(expectedLine, actualLine, "Comparisen failed at line " + (i + 1));
            }
        }
    }
}
