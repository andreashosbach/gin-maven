package gin;

import gin.cucumberjson.CucumberJsonWrapper;
import gin.cucumberjson.CucumberTestResultIntegrator;
import gin.featuresjson.FeaturesJsonFactory;
import gin.html.HtmlFactory;
import gin.model.FeatureSuite;
import gin.scenarioo.ScenariooFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.net.URISyntaxException;
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

    @Parameter(property = "output", defaultValue = "DHTML", required = false)
    private String output;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            setLogLevel();
            FeatureSuite featureSuite = FeatureSuite.fromPath(featureFiles);
            featureSuite.setApplicationName(project.getName());
            featureSuite.setApplicationVersion(project.getVersion());

            logger.info("Read feature files");

            if (resultFile != null && !resultFile.isEmpty()) {
                try {
                    CucumberJsonWrapper testResult = CucumberJsonWrapper.fromFile(resultFile);
                    new CucumberTestResultIntegrator(featureSuite).integrateFromCucumberJson(testResult);
                    logger.info("Integrated test results");
                } catch (IOException e) {
                    logger.severe(e.getMessage());
                }
            }

            OutputFormatFactory factory;
            switch (output.toUpperCase()) {
                case "DHTML":
                    factory = new FeaturesJsonFactory(featureSuite);
                    break;
                case "HTML":
                    factory = new HtmlFactory(featureSuite);
                    break;
                case "SCENARIOO":
                    factory = new ScenariooFactory(featureSuite);
                    break;
                default:
                    logger.severe("No output format given.");
                    return;
            }

            OutputFormatGenerator generator = factory.buildOutputGenerator();
            generator.writeOutput(outputDirectory);
            logger.info("Successfully wrote output to: '" + outputDirectory + "'");
            System.out.println("Generated Living Documentation at: '" + outputDirectory);

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
