package pl.targosz.invoicing.model

import junit.framework.TestCase
import spock.lang.Specification

class InvoiceEntryTest extends Specification {

    def "should add new invoice entry"() {
        given:
        def invoiceEntry = new InvoiceEntry("Adidas Pro Black",BigDecimal.valueOf(100),BigDecimal.valueOf(8),Vat.VAT_8)

        when:
        def result = invoiceEntry

        then:

        result != null
    }
}
