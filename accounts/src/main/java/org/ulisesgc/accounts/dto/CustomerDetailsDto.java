package org.ulisesgc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetailsDto",
        description = "Schema to hold Customer, Accounts, Cards and Loans Information"
)
public class CustomerDetailsDto {
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

    @Schema (
            description = "account details of customer"
    )
    private CardsDto cardDTO;

    @Schema (
            description = "account details of customer"
    )
    private LoansDto loansDTO;
}

