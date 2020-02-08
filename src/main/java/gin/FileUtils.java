package gin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.logging.Logger;

public class FileUtils {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
        FileSystem fileSystem = FileSystems.newFileSystem(GinMojo.class.getResource("").toURI(), Collections.<String, String>emptyMap());
        Path jarPath = fileSystem.getPath(source);

        Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String directory = dir.toString().replaceFirst("/", "");
                logger.info("Create directory: " + target.resolve(directory));
                Files.createDirectories(target.resolve(directory));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String destination = file.toString().replaceFirst("/", "");
                InputStream in = GinCLI.class.getClassLoader().getResourceAsStream(destination);
                Path destinationPath = target.resolve(Paths.get(destination));
                logger.info("Copy file: source " + file.toString() + " destination " + destination);
                Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void saveToFile(String textToSave, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(textToSave);
        }

    }
}
