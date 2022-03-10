package pl.targosz.invoicing.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import pl.targosz.invoicing.database.InvoiceRepository;
import pl.targosz.invoicing.model.Invoice;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }


    public Optional<Invoice> getById(UUID id) throws IOException {
        return invoiceRepository.getById(id);
    }

    public List<Invoice> getAll() throws IOException {
        return invoiceRepository.getAll();
    }


    public void update(UUID id, Invoice updatedInvoice) {
        invoiceRepository.update(id, updatedInvoice);
    }

    public void delete(UUID id) throws IOException {
        invoiceRepository.delete(id);
    }


}
