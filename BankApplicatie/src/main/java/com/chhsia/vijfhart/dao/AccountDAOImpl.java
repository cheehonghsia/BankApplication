package com.chhsia.vijfhart.dao;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chhsia.vijfhart.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	@Resource private SessionFactory sessionFactory;
	
	
	public void saveAccount(Account account){	
		
		sessionFactory.getCurrentSession().persist(account);
		
	}
	
	public void updateAccount(Account account){
		
		sessionFactory.getCurrentSession().saveOrUpdate(account);
		
	}
	
	public Account loadAccount(long accountnumber){
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		
		criteria.add(Restrictions.eq("accountnumber", accountnumber));
		
		return (Account) criteria.uniqueResult();
		
	}

	public Collection<Account> getAccounts() {
		
		return sessionFactory.getCurrentSession().createCriteria(Account.class).list();
	}

}
