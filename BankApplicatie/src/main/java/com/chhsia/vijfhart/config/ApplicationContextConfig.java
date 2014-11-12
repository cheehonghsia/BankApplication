package com.chhsia.vijfhart.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.chhsia.vijfhart.service.AccountService;
import com.chhsia.vijfhart.service.AccountServiceImpl;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource("classpath:database.properties")
@ComponentScan("com.chhsia.vijfhart")
public class ApplicationContextConfig {

	@Resource
	private Environment env;

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";

	@Bean(name = "accountService")
	public AccountService accountService() {
		return new AccountServiceImpl();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}
	
	 @Bean
	 public HibernateTransactionManager transactionManager() {
	  HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	  transactionManager.setSessionFactory(sessionFactory().getObject());
	  return transactionManager;
	 }
	  
	 @Bean
	 public LocalSessionFactoryBean sessionFactory() {
	  LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	  sessionFactoryBean.setDataSource(dataSource());
	  sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
	  sessionFactoryBean.setHibernateProperties(hibProperties());
	  return sessionFactoryBean;
	 }	
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL));
		return properties; 
	}	

}
