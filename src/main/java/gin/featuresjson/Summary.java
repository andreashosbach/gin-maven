package gin.featuresjson;

import java.util.List;

public class Summary {
    public List<TagResultSummary> Tags;
    public List<FolderResultSummary> Folders;
    public List<FolderResultSummary> NotTestedFolders;
    public ResultSummary Scenarios;
    public ResultSummary Features;
}
