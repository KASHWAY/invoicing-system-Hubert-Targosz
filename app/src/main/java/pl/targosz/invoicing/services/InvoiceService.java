package pl.targosz.invoicing.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import pl.targosz.invoicing.database.InvoiceRepository;
import pl.targosz.invoicing.model.Invoice;

@Service
public class InvoiceService implements InvoiceRepository {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> getById(UUID id) throws IOException {
        return invoiceRepository.getById(id);
    }

    @Override
    public List<Invoice> getAll() throws IOException {
        return invoiceRepository.getAll();
    }

    @Override
    public void update(UUID id, Invoice updatedInvoice) {
        invoiceRepository.update(id, updatedInvoice);
    }

    @Override
    public void delete(UUID id) throws IOException {
        invoiceRepository.delete(id);
    }

    @Override
    public void clear() {

    }

}
