package gin.model;

import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Example {
    private String name;
    private List<String> headerRow = new ArrayList<>();
    private List<List<String>> dataRows = new ArrayList<>();
    private List<Integer> dataRowLines = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private String keyword;

    public static Example fromGherkin(Messages.GherkinDocument.Feature.Scenario.Examples example) {
        Example pExample = new Example();
        pExample.name = example.getName();
        example.getTagsList().stream().forEachOrdered(t -> pExample.tags.add(t.getName()));
        pExample.keyword = example.getKeyword().trim();

        Messages.GherkinDocument.Feature.TableRow header = example.getTableHeader();
        header.getCellsList().stream().forEachOrdered(c -> pExample.headerRow.add(c.getValue()));

        for (Messages.GherkinDocument.Feature.TableRow row : example.getTableBodyList()) {
            List<String> pRow = new ArrayList<>();
            row.getCellsList().stream().forEachOrdered(c -> pRow.add(c.getValue()));
            pExample.dataRows.add(pRow);
            pExample.dataRowLines.add(row.getLocation().getLine());
        }
        return pExample;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<String> getHeaders() {
        return headerRow;
    }

    public List<List<String>> getDataRows() {
        return dataRows;
    }

    public List<Integer> getDataRowLines(){
        return dataRowLines;
    }
}
