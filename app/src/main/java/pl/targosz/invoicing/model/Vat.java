package pl.targosz.invoicing.model;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Vat {

    VAT_0(0),
    VAT_5(0.5f),
    VAT_8(0.8f),
    VAT_23(0.23f);

    private final float rate;

    Vat(int rate) {
        this.rate = rate;
    }
}

