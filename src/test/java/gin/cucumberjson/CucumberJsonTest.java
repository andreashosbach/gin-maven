package gin.cucumberjson;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CucumberJsonTest {
    @Test
    public void readJson() throws IOException {
        String resultFile = CucumberJsonTest.class.getResource("/cucumber.json").getFile();
        resultFile = resultFile.replaceFirst("/", "");

        CucumberJsonWrapper cucumberJson = CucumberJsonWrapper.fromFile(resultFile);

        assertEquals(1, cucumberJson.featuresResults.size());
        assertEquals("Feature Name", cucumberJson.featuresResults.get(0).name);
    }
}
