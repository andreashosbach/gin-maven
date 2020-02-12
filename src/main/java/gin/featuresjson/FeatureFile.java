package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cucumber.gherkin.GherkinDocumentBuilder;
import io.cucumber.gherkin.Parser;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Collections.singletonList;

public class FeatureFile {
    @JsonIgnore
    public String filePath;

    public String RelativeFolder;
    public Feature Feature;
    public Result Result;

    public static FeatureFile fromFile(File file, String basePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder gherkin = new StringBuilder();
        while ((line = br.readLine()) != null) {
            gherkin.append(line);
            gherkin.append("\n");
        }

        Parser<Messages.GherkinDocument.Builder> parser = new Parser<>(new GherkinDocumentBuilder(new IdGenerator() {
            @Override
            public String newId() {
                return java.util.UUID.randomUUID().toString();
            }
        }));
        Messages.GherkinDocument doc = parser.parse(gherkin.toString()).build();

        FeatureFile pFeatures = new FeatureFile();
        pFeatures.filePath = file.getCanonicalPath();
        pFeatures.RelativeFolder = Paths.get(basePath).relativize(Paths.get(file.getCanonicalPath())).toString();
        pFeatures.Feature = gin.featuresjson.Feature.fromGherkin(doc.getFeature());
        pFeatures.Result = new Result();

        return pFeatures;
    }
}

