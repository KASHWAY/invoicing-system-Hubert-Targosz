package pl.targosz.invoicing.database

import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDateTime;


abstract class RepositoryTest extends Specification {

    InvoiceRepository repository

    abstract InvoiceRepository getInvoiceRepository()

    def createdAt = LocalDateTime.now()
    def firstEntry = new InvoiceEntry("Dom", 1000000 as BigDecimal, 23 as BigDecimal, Vat.VAT_23)
    def buyer = new Company("1234567891", "KashwayCompany", "Ul. Fiołkowa 15, Kraków")
    def seller = new Company("3373187493", "Hubert Targosz", "Al. 3 Wieszczy 17/5, Kraków")
    def entries1 = Arrays.asList(firstEntry)
    UUID id
    def invoice = new Invoice(id, createdAt, seller, buyer, entries1)
    def updatedInvoice = new Invoice(id, createdAt, seller, buyer, entries1)

    def setup(){
        repository = getInvoiceRepository()
    }

    def "should save invoice"()

    {
        when:
        def result = repository.save(invoice)

        then:

        repository.getById(invoice.getId()) != null
        repository.getById(invoice.getId()).get().getSeller().getName() == "Hubert Targosz"

    }

    def "should search for invoice with id"()

    {
        when:

        repository.save(invoice)
        def result = invoice


        then:
        result == repository.getById(invoice.getId())

    }

    def "should get all values"()

    {

        when:

        repository.save(invoice)

        then:

        repository.getAll()

    }

    def "should update id"()

    {

        when:
        repository.save(invoice)

        repository.update(invoice.getId(), updatedInvoice)

        then:
        invoice.getId() == updatedInvoice.getId()

    }

    def "should throw exception"()

    {

        when:
        repository.update(id, updatedInvoice)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "There is no id like : " + id
    }

    def "should delete ID "()

    {
        given:
        UUID id = UUID.randomUUID()

        when:
        invoice.setId(id)
        repository.delete(invoice.getId())

        then:
        !repository.invoices.containsKey(id)
    }

}