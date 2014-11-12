package com.chhsia.vijfhart.service;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chhsia.vijfhart.dao.AccountDAO;
import com.chhsia.vijfhart.model.Account;
import com.chhsia.vijfhart.model.Customer;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	
	@InjectMocks private AccountServiceImpl accountService;
	@Mock private AccountDAO accountDAO;

	@Test
	public void testTransferFunds() {
		
		Customer customerA = new Customer("Cheehong");
		Customer customerB = new Customer("Pavel");
		
		Account accountA = new Account(100l);
		accountA.setCustomer(customerA);
		
		Account accountB = new Account(200l);
		accountB.setCustomer(customerB);
		
		when(accountDAO.loadAccount(100l)).thenReturn(accountA);
		when(accountDAO.loadAccount(200l)).thenReturn(accountB);

		accountService.transferFunds(100l, 200l, 500.23, "testdescription");
		
		Assert.assertEquals(-500.23, accountA.getEntryList().iterator().next().getAmount());
		
	}	
	
	@Test
	public void testTransferFundsRollback() {
		
		Customer customerA = new Customer("Cheehong");
		Customer customerB = new Customer("Pavel");
		
		Account accountA = new Account(100l);
		accountA.setCustomer(customerA);
		
		Account accountB = new Account(200l);
		accountB.setCustomer(customerB);
		
		when(accountDAO.loadAccount(100l)).thenReturn(accountA);
		when(accountDAO.loadAccount(200l)).thenReturn(accountB);
		doThrow(new RuntimeException()).when(accountDAO).updateAccount(accountB);

		try{
			
			accountService.transferFunds(100l, 200l, 500.23, "testdescription");
			
		} catch(RuntimeException re){
			
			Account retAccount = accountDAO.loadAccount(100l);
			Assert.assertEquals(0, retAccount.getEntryList().iterator().next().getAmount());
			
		}
		

	}	

}
