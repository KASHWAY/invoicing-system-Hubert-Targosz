package pl.targosz.invoicing.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Company {

    private String taxIdentifier;
    private String name;
    private String address;

}
