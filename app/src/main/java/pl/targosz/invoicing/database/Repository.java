package pl.targosz.invoicing.database;

import java.util.List;
import java.util.UUID;
import pl.targosz.invoicing.model.Invoice;

public interface Repository {

    Invoice save(Invoice invoice);

    Invoice getById(UUID id);

    List<Invoice> getAll();

    void update(UUID id, Invoice updatedInvoice);

    void delete(UUID id);

}
