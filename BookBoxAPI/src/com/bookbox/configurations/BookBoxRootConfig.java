package com.bookbox.configurations;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@ComponentScan(basePackages={"com.bookbox"},
excludeFilters={
@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
})
public class BookBoxRootConfig {
	@Bean
	public BasicDataSource dataSource() {
	BasicDataSource ds = new BasicDataSource();
	ds.setDriverClassName("com.mysql.jdbc.Driver");
	ds.setUrl("jdbc:mysql://localhost:3306/bookbox?useUnicode=true&useFastDateParsing=false&characterEncoding=UTF-8");
	ds.setUsername("root");
	ds.setPassword("varun");
	ds.setInitialSize(5);
	ds.setMaxActive(10);
	return ds;
	}
	
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	sfb.setDataSource(dataSource);
	sfb.setPackagesToScan(new String[] { "com.bookbox.models" });
	Properties props = new Properties();
	props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	props.setProperty("hibernate.show_sql", "true");
	props.setProperty("hibernate.current_session_context_class", "thread");
	props.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	props.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bookbox?useUnicode=true&useFastDateParsing=false&characterEncoding=UTF-8");
	props.setProperty("hibernate.connection.username", "root");
	props.setProperty("hibernate.connection.password", "varun");
	props.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
	//props.setProperty("hibernate.c3p0.max_size", "25");
	sfb.setHibernateProperties(props);
	return sfb;
	}
}
