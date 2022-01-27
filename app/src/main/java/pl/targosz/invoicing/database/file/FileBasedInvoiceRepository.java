package pl.targosz.invoicing.database.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.targosz.invoicing.config.FileConfiguration;
import pl.targosz.invoicing.database.InvoiceRepository;
import pl.targosz.invoicing.model.Invoice;
import pl.targosz.invoicing.services.FileService;
import pl.targosz.invoicing.services.JsonService;

@AllArgsConstructor
public class FileBasedInvoiceRepository implements InvoiceRepository {

    private final FileService fileService = new FileService();
    private final JsonService<Invoice> jsonService = new JsonService<>();
    private final JsonService<Invoice> jsonServiceId = new JsonService<Invoice>();


    @Override
    public Invoice save(Invoice invoice) {
        invoice.setId(UUID.randomUUID());
        try {
            String json = jsonService.toJson(invoice);
            fileService.writeToFile(json);

            return invoice;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Optional<Invoice> getById(UUID id) throws IOException {
        return Optional.of(getAll()
            .stream()
            .filter(invoice -> invoice.getId().equals(id))
            .findFirst()
            .get());

    }

    @Override
    public List<Invoice> getAll() throws IOException {
        return fileService.readFile().map(
            item -> {
                try {
                    return jsonService.toObject(item, Invoice.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public void update(UUID id, Invoice updatedInvoice) {
        if (containsInRepository(id)) {
            delete(id);
            updatedInvoice.setId(id);
            fileService.writeToFile(jsonService.toJson(updatedInvoice));
            fileService.writeToFile(jsonServiceId.toJson(updatedInvoice));

        } else {
            throw new NoSuchObjectException("There is no invoice with id like : " + id + ". Add a new invoice please.");
        }


    }

    @Override
    public void delete(UUID id) throws IOException {
        List<Invoice> invoices = fileService.readFile().map(item -> {
            try {
                return jsonService.toObject(item, Invoice.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        invoices.removeIf(item -> item.getId().equals(id));

        fileService.writeToFile(String.join(invoices.stream().map(item -> {
            try {
                return jsonService.toJson(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        }).collect(Collectors.joining(System.lineSeparator()))), StandardOpenOption.WRITE);
    }

    public boolean containsInRepository(UUID id) {
        try {
            return Files.readAllLines(Paths.get(FileConfiguration.ID_DB_PATH)).stream().anyMatch(ids -> ids.contains(id.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
