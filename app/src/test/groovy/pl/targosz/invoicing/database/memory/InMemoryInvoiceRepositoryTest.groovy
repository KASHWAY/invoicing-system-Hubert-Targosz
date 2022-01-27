package pl.targosz.invoicing.database.memory

import pl.targosz.invoicing.database.InvoiceRepository
import pl.targosz.invoicing.model.Company
import pl.targosz.invoicing.model.Invoice
import pl.targosz.invoicing.model.InvoiceEntry
import pl.targosz.invoicing.model.Vat
import spock.lang.Specification
import java.time.LocalDateTime

class InMemoryInvoiceRepositoryTest extends Specification {

    InMemoryInvoiceRepository inMemoryRepository = new InMemoryInvoiceRepository()
    def createdAt = LocalDateTime.now()
    def firstEntry = new InvoiceEntry("Dom", 1000000 as BigDecimal, 23 as BigDecimal, Vat.VAT_23)
    def buyer = new Company("1234567891", "KashwayCompany", "Ul. Fiołkowa 15, Kraków")
    def seller = new Company("3373187493", "Hubert Targosz", "Al. 3 Wieszczy 17/5, Kraków")
    def entries1 = Arrays.asList(firstEntry)
    UUID id
    def invoice = new Invoice(id, createdAt, seller, buyer, entries1)
    def updatedInvoice = new Invoice(id, createdAt, seller, buyer, entries1)

    def "should save invoice"() {
        when:
        InvoiceRepository repository = new InMemoryInvoiceRepository()
        def result = repository.save(invoice)

        then:

        repository.getById(invoice.getId()) != null
        // todo tu jest problem taki że repository.getById zwraca obiekt Optional a nie Invoice
        //  - musisz najpierw wyciągnąć z optionala obiekt invoice z pomocą metody .get() lub podobnych
        repository.getById(invoice.getId()).getSeller().getName() == "Hubert Targosz"

    }

    def "should search for invoice with id"() {
        when:

        inMemoryRepository.save(invoice)
        def result = invoice


        then:
        result == inMemoryRepository.getById(invoice.getId())

    }

    def "should get all values"() {

        when:

        inMemoryRepository.save(invoice)

        then:

        inMemoryRepository.getAll()

    }

    def "should update id"() {

        when:
        inMemoryRepository.save(invoice)

        inMemoryRepository.update(invoice.getId(), updatedInvoice)

        then:
        invoice.getId() == updatedInvoice.getId()

    }

    def "should throw exception"() {

        when:
        inMemoryRepository.update(id, updatedInvoice)

        then:
        def e =
                thrown(IllegalArgumentException)
        e.message == "There is no id like : " + id
    }

    def "should delete ID "() {
        given:
        UUID id = UUID.randomUUID()

        when:
        invoice.setId(id)
        inMemoryRepository.delete(invoice.getId())

        then:
        !inMemoryRepository.invoices.containsKey(id)
    }

}
