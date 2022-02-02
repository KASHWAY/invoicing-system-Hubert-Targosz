package pl.targosz.invoicing.database.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.targosz.invoicing.database.InvoiceRepository;
import pl.targosz.invoicing.model.Invoice;
import pl.targosz.invoicing.services.FileService;
import pl.targosz.invoicing.services.JsonService;

@RequiredArgsConstructor
public class FileBasedInvoiceRepository implements InvoiceRepository {

    private final FileService invoicesFileService;
    private final FileService idsFilesService;
    private final JsonService<Invoice> jsonService;

    @Override
    public Invoice save(Invoice invoice) {
        invoice.setId(UUID.randomUUID());
        try {
            String json = jsonService.toJson(invoice);
            invoicesFileService.writeToFile(json);
            idsFilesService.writeToFile(invoice.getId().toString());
            return invoice;
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong invoice data");
        }
    }

    @Override
    public Optional<Invoice> getById(UUID id) throws IOException {
        return getAll().stream().filter(invoice -> invoice.getId().equals(id)).findFirst();

    }

    @Override
    public List<Invoice> getAll() throws IOException {
        return invoicesFileService.readFile().map(
            item -> {
                try {
                    return jsonService.toObject(item, Invoice.class);
                } catch (JsonProcessingException e) {
                    e.getMessage();
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
            invoicesFileService.writeToFile(jsonService.toJson(updatedInvoice));
        } else {
            throw new IllegalArgumentException("There is no invoice with id like : " + id + ". Add a new invoice please.");
        }

    }

    @Override
    public void delete(UUID id) throws IOException {
        List<Invoice> invoices = getAll();
        invoices.removeIf(item -> item.getId().equals(id));
        invoicesFileService.clearAllFiles();
        invoices.forEach(this::save);
    }

    public boolean containsInRepository(UUID id) {
        try {
            return idsFilesService.readFile().anyMatch(ids -> ids.contains(id.toString()));
        } catch (IOException e) {
            return false;
        }

    }

    public void clear() {
        invoicesFileService.clearAllFiles();
    }

}
