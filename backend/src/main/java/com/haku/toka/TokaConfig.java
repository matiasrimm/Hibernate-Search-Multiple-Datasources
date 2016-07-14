package com.haku.toka;

import java.util.HashMap;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = "tokaEntityManagerFactory",
		transactionManagerRef = "tokaTransactionManager")
public class TokaConfig {

	@Autowired
	Environment env;
	
	@Bean
	PlatformTransactionManager tokaTransactionManager() {
		return new JpaTransactionManager(tokaEntityManagerFactory().getObject());
	}
	
	@Bean    
    public LocalContainerEntityManagerFactoryBean tokaEntityManagerFactory() {
		
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.haku.toka" });
        em.setPersistenceUnitName("tokaUnit");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        
        em.setJpaPropertyMap(properties);
 
        return em;
    }     
 	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbcToka.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbcToka.url"));
		dataSource.setUsername(env.getProperty("jdbcToka.username"));
		dataSource.setPassword(env.getProperty("jdbcToka.password"));	 
		return dataSource;
	}
	
}
