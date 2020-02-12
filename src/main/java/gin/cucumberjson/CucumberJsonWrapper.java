package gin.cucumberjson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CucumberJsonWrapper {
        public List<TestFile> featuresResults;

        public static CucumberJsonWrapper fromFile(String filename) throws IOException {
                StringBuilder contentBuilder = new StringBuilder();
                if(!Files.exists(Paths.get(filename))){
                        throw new IOException("File not found " + filename);
                }
                try (Stream<String> stream = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
                        stream.forEach(s -> contentBuilder.append(s).append("\n"));
                }
                String cucumberJson = contentBuilder.toString();
                ObjectMapper mapper = new ObjectMapper();
                TestFile[] testResults = mapper.readValue(cucumberJson, TestFile[].class);

                CucumberJsonWrapper run = new CucumberJsonWrapper();
                run.featuresResults = Arrays.asList(testResults);
                return run;
        }
}
