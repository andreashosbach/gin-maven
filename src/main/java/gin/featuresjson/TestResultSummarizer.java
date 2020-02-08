package gin.featuresjson;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TestResultSummarizer {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private FeaturesJsonWrapper fWrapper;
    private Map<String, TagResultSummary> tagResults = new HashMap<>();
    private Map<String, FolderResultSummary> folderResults = new HashMap<>();

    public TestResultSummarizer(FeaturesJsonWrapper fWrapper) {
        this.fWrapper = fWrapper;
    }

    public void summarize() {
        for (FeatureFile featureFile : fWrapper.Features) {
            evaluateTags(featureFile.Feature.Tags, featureFile.Feature.Result);
            evalaluateFolder(featureFile.RelativeFolder, featureFile.Result);

            if (featureFile.Feature.Background != null) {
                fWrapper.Summary.Scenarios.Total++;
                evaluateResult(featureFile.Feature.Background.Result, fWrapper.Summary.Scenarios);
                evaluateTags(featureFile.Feature.Background.Tags, featureFile.Feature.Background.Result);
            }

            for (Scenario scenario : featureFile.Feature.FeatureElements) {
                fWrapper.Summary.Scenarios.Total++;
                evaluateResult(scenario.Result, fWrapper.Summary.Scenarios);
                evaluateTags(scenario.Tags, scenario.Result);
            }
        }
        fWrapper.Summary.Tags.addAll(tagResults.values());
        fWrapper.Summary.Folders.addAll(folderResults.values());
    }

    private void evalaluateFolder(final String relativeFolder, final Result result) {
        logger.info("Summarizing folder " + relativeFolder);
        Path path = Paths.get(relativeFolder);
        String folder = "root";
        if (path.getParent() != null) {
            folder = path.getParent().getFileName().toString();
        }
        FolderResultSummary summary = folderResults.get(folder);
        if (summary == null) {
            summary = new FolderResultSummary();
            summary.Folder = folder;
            folderResults.put(folder, summary);
        }

        evaluateResult(result, summary);
    }

    private void evaluateTags(final List<String> tags, Result result) {
        logger.info("Summarizing tags");
        for (String tag : tags) {
            TagResultSummary summary = tagResults.get(tag);
            if (summary == null) {
                summary = new TagResultSummary();
                summary.Tag = tag;
                tagResults.put(tag, summary);
            }
            evaluateResult(result, summary);
        }
    }

    private void evaluateResult(Result result, ResultSummary summary) {
        if (result.WasExecuted && result.WasProvided) {
            if (result.WasSuccessful) {
                summary.Passing++;
            } else {
                summary.Failing++;
            }
        } else {
            summary.Inconclusive++;
        }
    }

}
