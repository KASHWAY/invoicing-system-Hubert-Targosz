package pl.targosz.invoicing.database;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.targosz.invoicing.model.Invoice;

public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    Optional<Invoice> getById(UUID id) throws IOException;

    List<Invoice> getAll() throws IOException;

    void update(UUID id, Invoice updatedInvoice);

    void delete(UUID id) throws IOException;


}
