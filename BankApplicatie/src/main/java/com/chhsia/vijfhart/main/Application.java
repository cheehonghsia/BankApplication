package com.chhsia.vijfhart.main;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.chhsia.vijfhart.config.ApplicationContextConfig;
import com.chhsia.vijfhart.model.Account;
import com.chhsia.vijfhart.model.AccountEntry;
import com.chhsia.vijfhart.service.AccountService;

public class Application {
	
	private static ApplicationContext context;

	public static void main(String[] args) {
		
		context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
		AccountService accountService = (AccountService) context.getBean("accountService");

		// create 2 accounts;
		accountService.createAccount(1263862, "Frank Brown");
		accountService.createAccount(4253892, "John Doe");
		//use account 1;
		accountService.deposit(1263862, 240);
		accountService.deposit(1263862, 529);
		accountService.withdrawEuros(1263862, 230);
		//use account 2;
		accountService.deposit(4253892, 12450);
		accountService.depositEuros(4253892, 200);
		accountService.transferFunds(4253892, 1263862, 100, "payment of invoice 10232");
		// show balances
		
		Collection<Account> accountlist=accountService.getAllAccounts();
		Iterator<Account> iterator = accountlist.iterator();
		while (iterator.hasNext()){
			Account account = (Account)iterator.next();
			System.out.println("Account with number= "+ account.getAccountnumber() +"--------------------------------------");
			Iterator<AccountEntry> entryIterator = account.getEntryList().iterator();
			while (entryIterator.hasNext()){
				AccountEntry entry= (AccountEntry)entryIterator.next();
				System.out.println("Date = "+ entry.getDate().toString() + "   Amount = "+ entry.getAmount() + "   Description = "+ entry.getDescription());	
			}
					
			System.out.println("Total balance = "+account.getBalance()+" dollars");		}
	}

}


