package com.chhsia.vijfhart.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chhsia.vijfhart.jms.JMSSender;
import com.chhsia.vijfhart.logging.Logger;

@Aspect
@Component
public class AccountServiceAdvice {
	
	@Autowired private Logger logger;
	@Autowired private JMSSender jmsSender;
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.createAccount(..)) && args(accountNumber, customerName)")
	public void createAccount(JoinPoint joinPoint, long accountNumber, String customerName){
		
		logger.log("createAccount with parameters accountNumber= "+accountNumber+" , customerName= "+customerName);
		
	}
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.withdraw(..)) && args(accountNumber, amount)")
	public void withdraw(JoinPoint joinPoint, long accountNumber, double amount){
		
		logger.log("withdraw with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		
	}
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.withdrawEuros(..)) && args(accountNumber, amount)")
	public void withdrawEuros(JoinPoint joinPoint, long accountNumber, double amount){
		
		logger.log("withdrawEuros with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		
	}
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.transferFunds(..)) && args(fromAccountNumber, toAccountNumber, amount, description)")
	public void transferFunds(JoinPoint joinPoint, long fromAccountNumber, long toAccountNumber, double amount, String description){
		
		logger.log("transferFunds with parameters fromAccountNumber= "+fromAccountNumber+" , toAccountNumber= "+toAccountNumber+" , amount= "+amount+" , description= "+description);
		
	}
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.depositEuros(..)) && args(accountNumber, amount)")
	public void depositEuros(JoinPoint joinPoint, long accountNumber, double amount){
		
		logger.log("depositEuros with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		
	}
	
	@After("execution(* com.chhsia.vijfhart.service.AccountServiceImpl.deposit(..)) && args(accountNumber, amount)")
	public void deposit(JoinPoint joinPoint, long accountNumber, double amount){
		
		logger.log("deposit with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		
		if (amount > 10000){
			
			jmsSender.sendJMSMessage("Deposit of $ "+amount+" to account with accountNumber= "+accountNumber);
		
		}
		
	}
		
}
