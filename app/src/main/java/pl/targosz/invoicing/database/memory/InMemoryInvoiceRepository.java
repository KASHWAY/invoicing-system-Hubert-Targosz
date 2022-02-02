package pl.targosz.invoicing.database.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import pl.targosz.invoicing.database.InvoiceRepository;
import pl.targosz.invoicing.model.Invoice;

public class InMemoryInvoiceRepository implements InvoiceRepository {

    protected Map<UUID, Invoice> invoices = new HashMap<>();

    @Override
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

    @Override
    public Optional<Invoice> getById(UUID id) {
        return Optional.of(invoices.get(id));
    }

    @Override
    public List<Invoice> getAll() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public void update(UUID id, Invoice updatedInvoice) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("There is no invoice with id like : " + id + ". Add a new invoice please.");
        }

        updatedInvoice.setId(id);
        invoices.put(id, updatedInvoice);
    }

    @Override
    public void delete(UUID id) {
        invoices.remove(id);
    }

    @Override
    public void clear() {
        invoices.clear();
    }

}
