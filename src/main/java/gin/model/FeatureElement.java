package gin.model;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureElement {
    private String name;
    private String description;
    private Location location;
    private String time;
    private List<String> tags = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void addStep(Step step) {
        steps.add(step);
    }

    protected void addTag(String tag) {
        tags.add(tag);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public List<String> getTags() {
        return tags;
    }

    public Location getLocation() {
        return location;
    }

    protected void setLocation(Location location) {
        this.location = location;
    }

    public abstract Result getResult();

    public boolean hasExamples() {
        return getExamples() != null && !getExamples().isEmpty();
    }

    public List<Example> getExamples() {
        return null;
    }

    public void setStartTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }
}
