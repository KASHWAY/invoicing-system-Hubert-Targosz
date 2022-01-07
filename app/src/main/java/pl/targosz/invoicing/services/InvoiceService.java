package pl.targosz.invoicing.services;

import java.util.List;
import java.util.UUID;
import pl.targosz.invoicing.database.Repository;
import pl.targosz.invoicing.model.Invoice;

public class InvoiceService implements Repository {

    private final Repository repository;

    public InvoiceService(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    @Override
    public Invoice getById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Invoice> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(UUID id, Invoice updatedInvoice) {
        repository.update(id, updatedInvoice);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
