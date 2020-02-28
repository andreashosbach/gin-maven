package gin.html;

import gin.OutputFormatGenerator;
import gin.model.Feature;
import gin.model.FeatureSuite;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlOutputGenerator implements OutputFormatGenerator {
    private FeatureSuite featureSuite;

    HtmlOutputGenerator(FeatureSuite featureSuite) {
        this.featureSuite = featureSuite;
    }

    public void writeOutput(String outputDirectory) throws IOException, URISyntaxException {
        if (Files.notExists(Paths.get(outputDirectory))) {
            Files.createDirectory(Paths.get(outputDirectory));
        }
        for (Feature feature : featureSuite.getFeatures()) {
            writeFeatureOutput(feature, outputDirectory);
        }
    }

    private void writeFeatureOutput(Feature feature, String outputDirectory) throws IOException {
        String html = generateHtmlOutput(feature);
        Path filename = Paths.get(feature.getFeatureFileName().replace(".feature", ".html")).getFileName();
        try (FileWriter fileWriter = new FileWriter(Paths.get(outputDirectory).resolve(filename).toString())) {
            fileWriter.write(html);
        }
    }

    public String generateHtmlOutput(Feature feature) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init();

        Template template = velocityEngine.getTemplate("template/feature.vm");

        VelocityContext context = new VelocityContext();
        context.put("feature", feature);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
