package gin.model;

import java.util.ArrayList;
import java.util.List;

public class FeatureElement {
    private String name;
    private String description;
    private Location location;
    private List<String> tags = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    public String getName(){
        return name;
    }

    protected void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    protected void setDescription(String description){
        this.description = description;
    }

    protected void addStep(Step step){
        steps.add(step);
    }

    protected void setLocation(Location location){
        this.location = location;
    }

    protected void addTag(String tag) {
        tags.add(tag);
    }
}
