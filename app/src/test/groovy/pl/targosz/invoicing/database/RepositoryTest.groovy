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
    def buyer = new Company("1234567891", "KashwayCompany", "Ul. XYZ 15/7 Warszawa")
    def buyer2 = new Company("456789123", "KFC", "Ul. Fiołkowa 15, Kraków")
    def seller = new Company("3373187493", "Mateusz Idiks", "Al. XYZ 5, POZNAN")
    def seller2 = new Company("7515212312", "Hubert Targosz", "Al. 3 Wieszczy 17/5, Kraków")
    def entries1 = Arrays.asList(firstEntry)
    UUID id
    def invoice = new Invoice(createdAt, seller, buyer, entries1)
    def invoice2 = new Invoice(createdAt, seller2, buyer2, entries1)
    def updatedInvoice = new Invoice(id, createdAt, seller, buyer, entries1)

    def setup() {
        repository = getInvoiceRepository()
    }

    def "should save invoice"()

    {
        when:
        repository.save(invoice)

        then:

        repository.getById(invoice.getId()).get() != null
        repository.getById(invoice.getId()).get().getSeller().getName() == "Mateusz Idiks"

    }

    def "should search for invoice with id"()

    {
        when:


        def result = repository.save(invoice)


        then:
        result.getId() == invoice.getId()

    }

    def "should get all values"()

    {

        given:

        repository.save(invoice)
        repository.save(invoice2)

        when:

        repository.getAll()
        then:
        repository.getAll().size() == 2


    }

    def "should update the invoice`s id"()

    {

        given:
        repository.save(invoice)
        when:
        repository.update(invoice.getId(),updatedInvoice)

        then:
        updatedInvoice.getId() == invoice.getId()

    }

    def "should throw exception"()

    {
        given:
        def id = UUID.randomUUID()
        when:
        repository.update(id, updatedInvoice)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "There is no invoice with id like : " + id + ". Add a new invoice please."
    }

    def "should delete invoice from a repository"()

    {
        given:
        repository.save(invoice)
        when:
        repository.delete(invoice.getId())
        then:
        repository.getAll().size() == 0
    }

    def "Should throw exception when there is no such id in a repository"() {
        given:
        def id = UUID.randomUUID()
        when:
        repository.update(id, invoice)
        then:
        thrown(IllegalArgumentException)
    }

}