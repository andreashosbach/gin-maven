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
}

