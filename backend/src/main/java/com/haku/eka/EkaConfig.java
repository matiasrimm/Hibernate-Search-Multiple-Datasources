package com.haku.eka;

import java.util.HashMap;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = "ekaEntityManagerFactory",
		transactionManagerRef = "ekaTransactionManager")
@PropertySource("classpath:application.properties")
public class EkaConfig {

	@Autowired
	Environment env;
	
	@Bean
	PlatformTransactionManager ekaTransactionManager() {
		return new JpaTransactionManager(ekaEntityManagerFactory().getObject());
	}
	
	@Bean    
    public LocalContainerEntityManagerFactoryBean ekaEntityManagerFactory() {
		
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(eDataSource());
        em.setPackagesToScan(new String[] { "com.haku.eka" });
        em.setPersistenceUnitName("ekaUnit");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        		
        em.setJpaPropertyMap(properties);
 
        return em;
    }     
 	
	@Bean
	public DataSource eDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbcEka.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbcEka.url"));
		dataSource.setUsername(env.getProperty("jdbcEka.username"));
		dataSource.setPassword(env.getProperty("jdbcEka.password"));	 
		return dataSource;
	}
	
}
