package gin.model;

import io.cucumber.gherkin.GherkinDocumentBuilder;
import io.cucumber.gherkin.Parser;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FeatureFile {

    public static Feature fromFile(File file, String basePath) throws IOException {

        Parser<Messages.GherkinDocument.Builder> parser = new Parser<>(new GherkinDocumentBuilder(new IdGenerator() {
            @Override
            public String newId() {
                return java.util.UUID.randomUUID().toString();
            }
        }));
        Messages.GherkinDocument doc = parser.parse(readFile(file)).build();

        Feature pFeature  = Feature.fromGherkin(doc.getFeature());
        pFeature.setFeatureFileName(file.getCanonicalPath());

        return pFeature;
    }

    private static String readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder gherkin = new StringBuilder();
        while ((line = br.readLine()) != null) {
            gherkin.append(line);
            gherkin.append("\n");
        }
        return gherkin.toString();
    }
}
