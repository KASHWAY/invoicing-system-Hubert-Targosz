package pl.targosz.invoicing.services

import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.database.memory.InMemoryInvoiceRepository
import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDateTime

class InvoiceServiceTest extends Specification {


    def createdAt = LocalDateTime.now()
    def firstEntry = new InvoiceEntry("Dom", 1000000 as BigDecimal, 23 as BigDecimal, Vat.VAT_23)
    def buyer = new Company("1234567891", "KashwayCompany", "Ul. Fiołkowa 15, Kraków")
    def seller = new Company("3373187493", "Hubert Targosz", "Al. 3 Wieszczy 17/5, Kraków")
    def entries1 = Arrays.asList(firstEntry)
    UUID id
    def invoice = new Invoice(id, createdAt, seller, buyer, entries1)
    def invoice2 = new Invoice (id,createdAt,seller,buyer,entries1)
    InvoiceRepository repository = new InMemoryInvoiceRepository()
    InvoiceService invoiceService = new InvoiceService(repository)


    def "should save an invoice to repository"() {
        when:
        def invoiceSave = invoiceService.save(invoice)

        then:
        repository.getById(invoiceSave.getId()).getBuyer().getName() == "KashwayCompany"

    }

    def "should get an invoice by id"() {
        when:
        def invoiceSave = invoiceService.save(invoice)

        then:

        repository.getById(invoiceSave.getId()).getSeller().getName() == "Hubert Targosz"

    }
    def "should get amount of invoices" (){

        when:
        invoiceService.save(invoice)
        invoiceService.save(invoice2)

        then:
        invoiceService.getAll().size() == 2
    }

    def "should update invoices" () {
        given:
        invoiceService.save(invoice)

        when:
        invoiceService.update(invoice.getId(),invoice2)

        then:
        invoice2.getId() == invoice.getId()

    }
    def "should delete an invoice"() {
        given:
        invoiceService.save(invoice)
        invoiceService.save(invoice2)
        when:
        invoiceService.delete(invoice.getId())
        then:
        invoiceService.getAll().size() == 1
    }

}
