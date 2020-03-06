package com.hatiko.ripple.database.config;

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
@EnableJpaRepositories(	basePackages = "com.hatiko.ripple.database.repo", 
						entityManagerFactoryRef = "userEntityManager", 
						transactionManagerRef = "userTransactionManager")
public class UserConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource userDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("database.driver"));
		dataSource.setUrl(env.getProperty("database.uri"));
		dataSource.setUsername(env.getProperty("database.user.name"));
		dataSource.setPassword(env.getProperty("database.user.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

		entityManager.setDataSource(userDataSource());
		entityManager.setPackagesToScan(new String[] { Constants.USER_PACKAGE });
		entityManager.setPersistenceUnitName(Constants.JPA_UNIT_USER);
		entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("database.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show-sql", env.getProperty("database.adress.jpa.show-sql"));

		entityManager.setJpaPropertyMap(properties);
		entityManager.afterPropertiesSet();

		return entityManager;
	}

	@Bean
	public PlatformTransactionManager userTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
		return transactionManager;
	}

}
