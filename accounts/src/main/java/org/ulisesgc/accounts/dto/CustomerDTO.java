package org.ulisesgc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema (
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDTO {

    @Schema (
            description = "name of customer",
            example = "Ulises GC"
    )
    @NotEmpty(message = "name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema (
            description = "email of customer",
            example = "ugarciacalderon@gmail.com"
    )
    @NotEmpty(message = "email can not be a null or empty")
    @Email(message = "email is not valid")
    private String email;

    @Schema (
            description = "number phone of customer",
            example = "123456789"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must can be 10 digits")
    private String mobileNumber;

    @Schema (
            description = "account details of customer"
    )
    private AccountsDTO accountsDTO;
}
