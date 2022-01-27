package pl.targosz.invoicing.services;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;
import pl.targosz.invoicing.config.FileConfiguration;

public class FileService {

    private final File invoiceDb;

    public FileService() {
        invoiceDb = new File(FileConfiguration.INVOICES_DB_PATH);
        try {
            Files.createFile(invoiceDb.toPath());
            Files.createFile(invoiceDb.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(final String line) throws IOException {
        Files.writeString(invoiceDb.toPath() , line + System.lineSeparator(), StandardOpenOption.APPEND);
    }
    public void writeToFile(final String line,StandardOpenOption openOption) throws IOException {
        Files.writeString(invoiceDb.toPath(),line + System.lineSeparator(),openOption);
    }
    public Stream<String> readFile() throws IOException {
        return Files.lines(invoiceDb.toPath());
    }

}
