package org.ulisesgc.accounts.service;

import org.ulisesgc.accounts.dto.CustomerDTO;

public interface AccountService {

    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccount(String mobibleNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);
}
