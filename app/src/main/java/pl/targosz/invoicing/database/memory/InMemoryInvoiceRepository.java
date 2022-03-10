package pl.targosz.invoicing.database.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.targosz.invoicing.model.Invoice;

@Repository
@ConditionalOnProperty(value = "app.db.memory", havingValue = "file", matchIfMissing = true)
public class InMemoryInvoiceRepository {

    protected Map<UUID, Invoice> invoices = new HashMap<>();

    public Invoice save(Invoice invoice) {
        UUID id = UUID.randomUUID();

        if (invoices.get(id) == null) {
            invoice.setId(id);
            invoices.put(id, invoice);

        } else {
            save(invoice);
        }

        return invoice;
    }

    public Optional<Invoice> getById(UUID id) {
        return Optional.of(invoices.get(id));
    }

    public List<Invoice> getAll() {
        return new ArrayList<>(invoices.values());
    }

    public void update(UUID id, Invoice updatedInvoice) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("There is no invoice with id like : " + id + ". Add a new invoice please.");
        }

        updatedInvoice.setId(id);
        invoices.put(id, updatedInvoice);
    }

    public void delete(UUID id) {
        invoices.remove(id);
    }

    public void clear() {
        invoices.clear();
    }

}
