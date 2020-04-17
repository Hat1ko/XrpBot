package com.hatiko.ripple.telegram.bot.database.config;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(	basePackages = "com.hatiko.ripple.telegram.bot.database.repo", 
						entityManagerFactoryRef = "xrpDatabaseEntityManager", 
						transactionManagerRef = "xrpDatabaseTransactionManager")
public class XrpDatabaseConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource xrpDatabaseDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("database.driver"));
		dataSource.setUrl(env.getProperty("database.uri"));
		dataSource.setUsername(env.getProperty("database.user.name"));
		dataSource.setPassword(env.getProperty("database.user.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean xrpDatabaseEntityManager() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

		entityManager.setDataSource(xrpDatabaseDataSource());
		entityManager.setPackagesToScan(new String[] { Constants.MODEL_PACKAGE });
		entityManager.setPersistenceUnitName(Constants.JPA_UNIT_XRP_DATABASE);
		entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("database.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show-sql", env.getProperty("database.jpa.show-sql"));

		entityManager.setJpaPropertyMap(properties);
		entityManager.afterPropertiesSet();

		return entityManager;
	}

	@Bean
	public PlatformTransactionManager xrpDatabaseTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(xrpDatabaseEntityManager().getObject());
		return transactionManager;
	}
}
