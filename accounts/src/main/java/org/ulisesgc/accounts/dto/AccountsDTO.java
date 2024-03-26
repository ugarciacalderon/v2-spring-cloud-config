package org.ulisesgc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountsDTO {
    @Schema(
            description = "account number of customer",
            example = "12545645"
    )
    @NotEmpty(message = "Account number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must can be 10 digits")
    private Long accountNumber;

    @Schema (
            description = "account type of customer",
            example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema (
            description = "branch addres of customer",
            example = "Main Street, New York"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
