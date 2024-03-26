package org.ulisesgc.accounts.mapper;

import org.ulisesgc.accounts.dto.AccountsDTO;
import org.ulisesgc.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDTO mapToAccountDTO(Accounts accounts, AccountsDTO accountsDTO) {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }

    public static Accounts mapToAccount(AccountsDTO accountsDTO, Accounts accounts) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accounts.getBranchAddress());
        return accounts;
    }
}
