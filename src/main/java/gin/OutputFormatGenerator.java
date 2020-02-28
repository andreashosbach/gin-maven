package gin;

import java.io.IOException;
import java.net.URISyntaxException;

public interface OutputFormatGenerator {
    public abstract void writeOutput(String outputDirectory) throws IOException, URISyntaxException;
}
