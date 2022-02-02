package pl.targosz.invoicing.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;
import pl.targosz.invoicing.config.FileConfiguration;

public class FileService {

    private final File file;

    public FileService(final String fileName) {

        file = new File(fileName);
        try {
            Files.createFile(file.toPath());
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public void writeToFile(final String line) throws IOException {
        Files.writeString(file.toPath(), line + System.lineSeparator(), StandardOpenOption.APPEND);

    }

    public void writeToFile(final String line, StandardOpenOption openOption) throws IOException {
        Files.writeString(file.toPath(), line + System.lineSeparator(), openOption);

    }

    public Stream<String> readFile() throws IOException {
        return Files.lines(file.toPath());
    }

    public void clearAllFiles() {
        try {
            Files.writeString(Paths.get(FileConfiguration.ID_DB_PATH), "");
            Files.writeString(Paths.get(FileConfiguration.INVOICES_DB_PATH), "");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
