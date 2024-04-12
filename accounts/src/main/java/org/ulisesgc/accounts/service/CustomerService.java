package org.ulisesgc.accounts.service;

import org.ulisesgc.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
