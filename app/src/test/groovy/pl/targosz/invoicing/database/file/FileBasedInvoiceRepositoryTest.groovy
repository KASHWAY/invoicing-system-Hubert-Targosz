package pl.targosz.invoicing.database.file

import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.database.RepositoryTest

class FileBasedInvoiceRepositoryTest extends RepositoryTest {


    @Override
    InvoiceRepository getInvoiceRepository() {
        return new FileBasedInvoiceRepository()
    }
}
