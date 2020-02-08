package gin.cucumberjson;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.logging.Logger;

public class TestCase {
    @JsonIgnore
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String start_timestamp;
    public int line;
    public String name;
    public String description;
    public String id;
    public String type;
    public String keyword;
    public List<TestStep> steps;
    public List<Tag> tags;
    public List<TestSetup> before;
    public List<TestSetup> after;

    public boolean isExecuted() {
        for(TestStep step: steps){
            if(!step.result.status.equals("skipped")){
                return true;
            }
        }
        logger.fine("All steps skipped for " + name);
        return false;
    }

    public boolean isSuccessful(){
        if(before != null) {
            for (TestSetup testSetup:before){
                if(!testSetup.result.status.equals("passed")){
                    return false;
                }
            }
        }
        if(after != null) {
            for (TestSetup testSetup:after){
                if(!testSetup.result.status.equals("passed")){
                    return false;
                }
            }
        }
        for(TestStep step: steps){
            if(!step.result.status.equals("passed")){
                return false;
            }
        }
        return true;
    }
}
