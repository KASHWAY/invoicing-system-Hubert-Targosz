package pl.targosz.invoicing.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;
import pl.targosz.invoicing.config.FileConfiguration;

public class FileService {

    private final File invoiceDb;
    private final File invoiceDb1;

    public FileService() {

        invoiceDb = new File(FileConfiguration.INVOICES_DB_PATH);
        invoiceDb1 = new File(FileConfiguration.ID_DB_PATH);
        try {
            Files.createFile(Paths.get(FileConfiguration.INVOICES_DB_PATH));
            Files.createFile(Paths.get(FileConfiguration.ID_DB_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void writeToFile(final String line) throws IOException {
        Files.writeString(invoiceDb.toPath(), line + System.lineSeparator(), StandardOpenOption.APPEND);
        Files.writeString(invoiceDb1.toPath(), line + System.lineSeparator(), StandardOpenOption.APPEND);
    }

    public void writeToFile(final String line, StandardOpenOption openOption) throws IOException {
        Files.writeString(invoiceDb.toPath(), line + System.lineSeparator(), openOption);
        Files.writeString(invoiceDb1.toPath(), line + System.lineSeparator(), StandardOpenOption.APPEND);
    }

    public Stream<String> readFile() throws IOException {
        return Files.lines(invoiceDb.toPath());
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
