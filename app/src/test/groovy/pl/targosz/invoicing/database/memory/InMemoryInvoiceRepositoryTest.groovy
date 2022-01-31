package pl.targosz.invoicing.database.memory

import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.database.RepositoryTest

class InMemoryInvoiceRepositoryTest extends RepositoryTest {

    @Override
    InvoiceRepository getInvoiceRepository() {
        return new InMemoryInvoiceRepository()
    }
}
