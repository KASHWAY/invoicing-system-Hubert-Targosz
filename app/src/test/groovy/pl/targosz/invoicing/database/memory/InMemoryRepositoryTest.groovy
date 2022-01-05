package pl.targosz.invoicing.database.memory

import pl.targosz.invoicing.database.Repository
import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDateTime

class InMemoryRepositoryTest extends Specification {

    private List<Invoice> invoices
    def createdAt = LocalDateTime.now()
    def firstEntry = new InvoiceEntry("Dom", 1000000 as BigDecimal, 23 as BigDecimal, Vat.VAT_23)
    def buyer = new Company("1234567891", "KashwayCompany", "Ul. Fiołkowa 15, Kraków")
    def seller = new Company("3373187493", "Hubert Targosz", "Al. 3 Wieszczy 17/5, Kraków")
    def entries1 = Arrays.asList(firstEntry)
    def repository = new InMemoryRepository()
    def invoice = new Invoice(UUID.randomUUID(), createdAt, seller, buyer, entries1)
    def updatedInvoice = new Invoice(UUID.randomUUID(), createdAt, seller, buyer, entries1)

    def "should save invoice"() {
        when:
        Repository repository = new InMemoryRepository()
        def result = repository.save(invoice)

        then:

        repository.getById(invoice.getId()) != null
        repository.getById(invoice.getId()).getSeller().getName() == "Hubert Targosz"

    }

    def "should search for invoice with id"() {
        when:

        repository.save(invoice)
        def result = repository.getById(invoice.getId())

        then:

        result.id == invoice.id
    }

    def "should get all values"() {
        when:

        repository.save(invoice)
        def result = new ArrayList<>(invoices.values())

        then:



    }

    def "should update id"() {
        given:

        UUID id = UUID.randomUUID()

        when:

        def result = invoices
        !result.containsKey(id)

        then:

        updatedInvoice.setId(id)
        result.put(id, updatedInvoice)

    }


}
