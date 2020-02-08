package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public String Name;
    public TableArgument TableArgument;
    public List<String> Tags;
    public String NativeKeyword;

    public static Example fromGherkin(Messages.GherkinDocument.Feature.Scenario.Examples example) {
        Example pExample = new Example();
        pExample.Name = example.getName();
        pExample.TableArgument = new Example.TableArgument();
        pExample.Tags = new ArrayList<>();
        example.getTagsList().stream().forEachOrdered(t -> pExample.Tags.add(t.getName()));
        pExample.NativeKeyword = example.getKeyword().trim();

        pExample.TableArgument.HeaderRow = new ArrayList<>();
        Messages.GherkinDocument.Feature.TableRow header = example.getTableHeader();
        header.getCellsList().stream().forEachOrdered(c -> pExample.TableArgument.HeaderRow.add(c.getValue()));

        pExample.TableArgument.DataRows = new ArrayList<>();
        pExample.TableArgument.DataRowLines = new ArrayList<>();
        for (Messages.GherkinDocument.Feature.TableRow row : example.getTableBodyList()) {
            List<String> pRow = new ArrayList<>();
            row.getCellsList().stream().forEachOrdered(c -> pRow.add(c.getValue()));
            pExample.TableArgument.DataRows.add(pRow);
            pExample.TableArgument.DataRowLines.add(row.getLocation().getLine());
        }
        return pExample;
    }

    public static class TableArgument {
        public List<String> HeaderRow;
        public List<List<String>> DataRows;
        @JsonIgnore
        public List<Integer> DataRowLines;
    }
}
