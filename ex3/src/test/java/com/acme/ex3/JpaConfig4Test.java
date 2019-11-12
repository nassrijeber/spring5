package com.acme.ex3;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class JpaConfig4Test {

	@Bean
	DataSource dsForTests() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).build();
	}
	
	@Bean
	FactoryBean<EntityManagerFactory> emfForTests(DataSource datasource){
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
		factoryBean.setDataSource(datasource);
		factoryBean.setPackagesToScan("com.acme.ex3.model");
		factoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");
		jpaProperties.put("javax.persistence.schema-generation.database.action", "create");
        jpaProperties.put("javax.persistence.sql-load-script-source", "test-data.sql");
        
		factoryBean.setJpaProperties(jpaProperties);
		
		// même si notre responsabilité se limite à la création d'une FactoryBean<EntityManagerFactory> (ici un LocalContainerEntityManagerFactoryBean), 
		// c'est au final un bean de type EntityManagerFactory qui sera inscrit dans le contexte 
		return factoryBean;
	}
}