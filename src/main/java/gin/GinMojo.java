package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.TestResultIntegrator;
import gin.featuresjson.FeaturesJsonWrapper;
import gin.featuresjson.TestResultSummarizer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Mojo(name = "gin", defaultPhase = LifecyclePhase.INSTALL)
public class GinMojo extends AbstractMojo {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Parameter(property = "feature_files", required = true)
    private String featureFiles;

    @Parameter(property = "result_file", required = true)
    private String resultFile;

    @Parameter(property = "output_directory", required = true)
    private String outputDirectory;

    @Parameter(property = "project", defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "loglevel", defaultValue = "SEVERE", required = true)
    private String loglevel;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            setLogLevel();

            FeaturesJsonWrapper fWrapper = FeaturesJsonWrapper.fromPath(featureFiles);
            fWrapper.Configuration.SutName = project.getName();
            fWrapper.Configuration.SutVersion = project.getVersion();
            logger.info("Read feature files");

            CucumberJsonWrapper testResult = CucumberJsonWrapper.fromFile(resultFile);
            new TestResultIntegrator(fWrapper).integrateFromCucumberJson(testResult);
            new TestResultSummarizer(fWrapper).summarize();
            logger.info("Integrated test results");

            String featuresJson = fWrapper.asFeaturesJson();
            FileUtils.copyFromJar("html", Paths.get(outputDirectory));
            FileUtils.saveToFile(featuresJson, outputDirectory + File.separator + "html" + File.separator + "features.js");
            logger.info("Succefully wrote output to: '" + outputDirectory + "'");

            System.out.println("Generated Living Documentation at: '" + outputDirectory + File.separator + "html'");
        } catch (IOException | URISyntaxException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
            throw new MojoFailureException(e.getMessage());
        }
    }

    private void setLogLevel() {
        Level level = Level.parse(loglevel);
        logger.setLevel(level);
        for (Handler h : logger.getHandlers()) {
            h.setLevel(level);
        }
    }
}
