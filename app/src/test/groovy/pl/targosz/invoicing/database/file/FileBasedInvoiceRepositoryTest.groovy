package pl.targosz.invoicing.database.file

import pl.targosz.invoicing.config.FileConfiguration
import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.database.RepositoryTest
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.services.FileService
import pl.targosz.invoicing.services.JsonService

class FileBasedInvoiceRepositoryTest extends RepositoryTest {

    InvoiceRepository getInvoiceRepository() {
        final FileService invoicesFileService = new FileService(FileConfiguration.INVOICES_DB_PATH)
        final FileService idsFilesService = new FileService(FileConfiguration.ID_DB_PATH)
        final JsonService<Invoice> jsonService = new JsonService<>()
        return new FileBasedInvoiceRepository(invoicesFileService, idsFilesService, jsonService)
    }

}
