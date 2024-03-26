package org.ulisesgc.accounts.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.ulisesgc.accounts.constants.AccountsConstants;
import org.ulisesgc.accounts.dto.AccountsDTO;
import org.ulisesgc.accounts.dto.CustomerDTO;
import org.ulisesgc.accounts.entity.Accounts;
import org.ulisesgc.accounts.entity.Customer;
import org.ulisesgc.accounts.exceptionHandling.CustomerAlreadyExistsException;
import org.ulisesgc.accounts.exceptionHandling.ResourceNotFoundException;
import org.ulisesgc.accounts.mapper.AccountsMapper;
import org.ulisesgc.accounts.mapper.CustomerMapper;
import org.ulisesgc.accounts.repository.AccountRepository;
import org.ulisesgc.accounts.repository.CustomerRepository;
import org.ulisesgc.accounts.service.AccountService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber"+customerDTO.getMobileNumber());
        }

        /*customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");*/ // this value are filled in the with the auditor ("auditAwareImpl")
        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer != null) {
            accountRepository.save(createNewAccount(savedCustomer));
        }
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        /*newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");*/ // this value are filled in the with the auditor ("auditAwareImpl")
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobibleNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobibleNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobibleNumber)
        );

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );

        CustomerDTO customerDto = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDto.setAccountsDTO(AccountsMapper.mapToAccountDTO(accounts, new AccountsDTO()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDto = customerDTO.getAccountsDTO();
        if(accountsDto !=null ){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccount(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDTO,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
