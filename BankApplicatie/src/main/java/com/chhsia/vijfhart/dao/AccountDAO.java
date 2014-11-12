package com.chhsia.vijfhart.dao;

import java.util.Collection;

import com.chhsia.vijfhart.model.Account;

public interface AccountDAO {
	
	public void saveAccount(Account account);
	
	public void updateAccount(Account account);
	
	public Account loadAccount(long accountnumber);
	
	public Collection<Account> getAccounts();
	
}
