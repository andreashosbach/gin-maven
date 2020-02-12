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

    public static class TableArgument {
        public List<String> HeaderRow;
        public List<List<String>> DataRows;
        @JsonIgnore
        public List<Integer> DataRowLines;
    }
}
