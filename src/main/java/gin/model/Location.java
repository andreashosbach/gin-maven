package gin.model;

import io.cucumber.messages.Messages;

public class Location {
    private int line;

    public static Location fromGherkin(Messages.Location location) {
        Location pLocation = new Location();
        pLocation.line = location.getLine();
        return pLocation;
    }

    public int getLine(){
        return line;
    }
}
