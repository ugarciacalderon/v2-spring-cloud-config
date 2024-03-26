package org.ulisesgc.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ulisesgc.accounts.constants.AccountsConstants;
import org.ulisesgc.accounts.dto.CustomerDTO;
import org.ulisesgc.accounts.dtoProperties.AccountsContactInfoDto;
import org.ulisesgc.accounts.responses.ErrorResponseDTO;
import org.ulisesgc.accounts.responses.ResponseDTO;
import org.ulisesgc.accounts.service.AccountService;

@Tag(
        name = "CRUD Rest Api for Accounts",
        description = "CRUD Rest API to create, read, update, delete"
)
@Controller
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor // con esta anotacion en el controller evitamos usar @Autowiring (buenas practicas)
@Validated
public class AccountsController {

    private final AccountService accountService;
    // buenas practicas para evitar el @Autowiring y el error ocasionado por la anotacion @AllArgsConstructor
    public AccountsController(AccountService accountService) {
        this.accountService=accountService;
    }

/*    @Value("${build.version}") esta anotacion debe de ser usada cuando tienes cientos de propiedades
    private String buildVersion;*/

/*    @Autowired Con esta Dependencia solo podemos leer propiedades del entorno, pero no de un archivo de propiedades
    private Environment environment;*/

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
            summary = "main endpoint",
            description = "This no required params"
    )
    @GetMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "Hello World!";
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Endpoint to create Account and Customer",
            description = "Rest API to create new Customer & Account inside"
    )
    @ResponseBody
    @PostMapping("/newAccount")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        accountService.createAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
   }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "REST API to fetch Customer & Account details based on a mobile number"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ResponseBody
   @GetMapping("/fetch")
   public ResponseEntity<CustomerDTO> fetchAccountDetails(@Valid @RequestParam
                                                             @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
    CustomerDTO customerDTO = accountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
   }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "REST API to fetch Customer & Account details based on a mobile number"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ResponseBody
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@Valid @RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Get contact information",
            description = "REST API get contact information"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
