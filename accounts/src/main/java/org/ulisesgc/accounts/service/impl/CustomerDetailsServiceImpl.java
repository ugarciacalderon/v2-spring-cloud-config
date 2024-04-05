package org.ulisesgc.accounts.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ulisesgc.accounts.dto.AccountsDTO;
import org.ulisesgc.accounts.dto.CardsDto;
import org.ulisesgc.accounts.dto.CustomerDetailsDto;
import org.ulisesgc.accounts.dto.LoansDto;
import org.ulisesgc.accounts.entity.Accounts;
import org.ulisesgc.accounts.entity.Customer;
import org.ulisesgc.accounts.exceptionHandling.ResourceNotFoundException;
import org.ulisesgc.accounts.mapper.AccountsMapper;
import org.ulisesgc.accounts.mapper.CustomerMapper;
import org.ulisesgc.accounts.repository.AccountRepository;
import org.ulisesgc.accounts.repository.CustomerRepository;
import org.ulisesgc.accounts.service.CustomerService;
import org.ulisesgc.accounts.service.client.CardsFeignClient;
import org.ulisesgc.accounts.service.client.LoansFeignClient;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements CustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomeDetailsDTO(customer, new CustomerDetailsDto());
        AccountsDTO accountsDTO = AccountsMapper.mapToAccountDTO(accounts, new AccountsDTO());
        customerDetailsDto.setAccountsDTO(accountsDTO);
        // se conectara con eureka para obtener los detalles del servicio de prestamos (loans) y realizar el balanceo
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);
        customerDetailsDto.setLoansDTO(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardDTO(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
