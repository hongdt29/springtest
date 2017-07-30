package summer.controller;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
//danh dau class dau tien de chay
//componet scan , scan package de lay thong tin, de search cac annotation trog package khac
//Autowire tu dong tao cac object cho minh, chi can khai bao Interface
@SpringBootApplication
@ComponentScan("summer.controller")
@ComponentScan("summer.db.client")
@ComponentScan("summer.db.entity")
@ComponentScan("summer.formmodel")
@ComponentScan("summer.service")
@MapperScan("summer.db.client")
public class SpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
		// ham dau tien chay vao khi run tren Spring Boot
		

	}
	@Bean
	public DataSource dataSource() {
	   //return new org.apache.tomcat.jdbc.pool.DataSource();
		DriverManagerDataSource  dataSource = new DriverManagerDataSource ();
	   dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	   dataSource.setUrl("jdbc:mysql://localhost:3306/locationtag");
	   dataSource.setUsername("root");
	   dataSource.setPassword("");
	   return dataSource;
	}
	@Bean
	   public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
	       SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	       sqlSessionFactoryBean.setDataSource(dataSource());
	       PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	       sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mappers/*.xml"));

	       return sqlSessionFactoryBean.getObject();
	   }

	   @Bean
	   public PlatformTransactionManager transactionManager() {
	       return new DataSourceTransactionManager(dataSource());
	   }

}
