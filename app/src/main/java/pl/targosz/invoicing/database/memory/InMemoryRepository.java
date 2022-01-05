package pl.targosz.invoicing.database.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import pl.targosz.invoicing.database.Repository;
import pl.targosz.invoicing.model.Invoice;


public class InMemoryRepository implements Repository {

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
    public Invoice getById(UUID id) {
        return invoices.get(id);
    }

    @Override
    public List<Invoice> getAll() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public void update(UUID id, Invoice updatedInvoice) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("There is no id like : " + id);
        }

        updatedInvoice.setId(id);
        invoices.put(id,updatedInvoice);
    }

    @Override
    public void delete(UUID id) {
        invoices.remove(id);
    }

}
