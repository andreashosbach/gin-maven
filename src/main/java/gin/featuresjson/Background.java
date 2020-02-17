package gin.featuresjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Background {
    @JsonIgnore
    public int line;

    public String Name;
    public String Description;
    public List<Step> Steps;
    public List<String> Tags;
    public Result Result;
}
