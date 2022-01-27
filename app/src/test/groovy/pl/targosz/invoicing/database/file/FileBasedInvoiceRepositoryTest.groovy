package pl.targosz.invoicing.database.file

import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDateTime

class FileBasedInvoiceRepositoryTest extends Specification {
    def buyer = new Company("123-456", "Hubert", "Kraków")
    def seller = new Company("987-654", "Adidas", "Bejing")
    def entry1 = new InvoiceEntry("Shoes", BigDecimal.valueOf(1000), BigDecimal.valueOf(230), Vat.VAT_23)
    def entry2 = new InvoiceEntry("Socks", BigDecimal.valueOf(500), BigDecimal.valueOf(115), Vat.VAT_23)
    def entries = Arrays.asList(entry1, entry2)
    def invoice = new Invoice(LocalDateTime.now(), seller, buyer, entries)
    def repository = new FileBasedInvoiceRepository()


    def "should add new invoice to a repository"() {
        when:
        Invoice savedInvoice = repository.save(invoice)
        then:
        repository.containsInRepository(savedInvoice.getId())
    }

    def "should get an invoice from a repository with id"() {
        given:
        repository.save(invoice)
        when:
        def result = repository.getById(invoice.getId())
        then:
        result.get().getSeller().getName() == "Adidas"
    }

    def "should update an invoice"() {
        when:
        repository.save(invoice)
        repository.update(invoice.getId(), invoice)
        then:
        repository.containsInRepository(invoice.getId())

    }

    def "should delete an invoice from repository" () {
        given:
        repository.save(invoice)
        when:
        repository.delete(invoice.getId())
        then:
        repository.getAll().size() == 0
    }


}
