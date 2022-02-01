package pl.targosz.invoicing.database.file

import junit.framework.TestCase
import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.database.RepositoryTest
import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDateTime

class FileBasedInvoiceRepositoryTest extends RepositoryTest {
   InvoiceRepository getInvoiceRepository () {
       return new FileBasedInvoiceRepository()
   }

}
