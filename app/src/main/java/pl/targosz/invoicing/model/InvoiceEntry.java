package pl.targosz.invoicing.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceEntry {

    private String description;
    private BigDecimal price;
    private BigDecimal vatValue;
    private Vat vatRate;

}
