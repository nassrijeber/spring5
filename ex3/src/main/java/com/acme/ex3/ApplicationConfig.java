package com.acme.ex3;

import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.persistence.metamodel.EntityType;
import javax.sql.DataSource;

import com.acme.common.EnableSlf4j;
import com.acme.common.service.impl.CommandProcessorImpl;
import com.acme.common.service.impl.DefaultExceptionHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jmx.export.annotation.ManagedOperation;

//@Configuration //sans spring boot
//@ComponentScan //sans spring boot
//@EnableAutoConfiguration //sans spring boot
@SpringBootApplication // remplace configuration et component scan et enableAutoConfiguration
/*@Import(ApplicationConfig.A.class)*/
//@EnableTransactionManagement(proxyTargetClass = true) // sans spring boot
//@PropertySource("classpath:application.properties") // sans spring boot
//@EnableJpaRepositories//(transactionManagerRef = "plaformTransactionManager")
@EnableScheduling
//@EnableMBeanExport // Active jmx // on peut l'enlever car les ctx sur les tests cause des probs et on peut le mettre lors de deployement avec un arguement lors du lancement de l'app
@ManagedResource
@Import({CommandProcessorImpl.class, DefaultExceptionHandler.class})
@EnableSlf4j
// sans spring boot :
// @EnableJpaRepositories
public class ApplicationConfig {

	private final Environment env;

	public ApplicationConfig(Environment env) {
		this.env = env;
	}
//sans spring boot
	/*@Bean
	DataSource ds() {
		HikariDataSource ds = new HikariDataSource();
		// TODO : valoriser les propriétés driverClassName, username et jdbcUrl.
		//Function<String, String> prop = env::getRequiredProperty;
		ds.setUsername(env.getRequiredProperty("datasource.username"));
		ds.setDriverClassName(env.getRequiredProperty("datasource.driver-class-name"));
		ds.setJdbcUrl(env.getRequiredProperty("datasource.url"));
		System.out.println(this.getClass());
		return ds;
	}*/

//	@Value("${datasource.url}")
//	private String jdbcUrl;
//	@Value("${datasource.username}")
//	private String username;
//	@Value("${datasource.driver-class-name}")
//	private String driverClassName;

//	@Bean
//	DataSource ds() {
//		System.out.println(driverClassName);
//		System.out.println(jdbcUrl);
//		System.out.println(username);
//		HikariDataSource ds = new HikariDataSource();
//		// TODO : valoriser les propriétés driverClassName, username et jdbcUrl.
//		ds.setDriverClassName(driverClassName);
//		ds.setJdbcUrl(jdbcUrl);
//		ds.setUsername(username);
//
//		return ds;
//	}
//sans spring boot
	/*@Bean
	FactoryBean<EntityManagerFactory> entityManagerFactory(){
		var factoryBean = new LocalContainerEntityManagerFactoryBean();
		// Nous reprenons ici la configuration autrefois écrite dans le fichier META-INF/persistence.xml
		factoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
		factoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		factoryBean.setPackagesToScan("com.acme.ex3.model");
		// TODO : valoriser la propriété dataSource de factoryBean
		factoryBean.setDataSource(ds());

		Properties jpaProperties = new Properties();
		// TODO : décommenter les deux lignes ci dessous (après avoir fait en sorte que la méthode ait accès aux propriétés du fichier application.properties)

		jpaProperties.put("hibernate.dialect", env.getProperty("jpa.properties.hibernate.dialect"));
		jpaProperties.put("hibernate.cache.region.factory_class", env.getProperty("jpa.properties.hibernate.cache.region.factory_class"));

		factoryBean.setJpaProperties(jpaProperties);

		return factoryBean;
	}

	@Bean
	PlatformTransactionManager plaformTransactionManager(EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}*/
//sans spring boot
/*	public static class A{
		@PersistenceContext
		private EntityManager em;

		@PostConstruct
		public void pc(){
			System.out.println(em.toString());
		}

		@Transactional
		public void foo(){
			System.out.println("entering foo");
			System.out.println("exiting foo");
		}
	}
*/

	@Autowired
	private EntityManagerFactory emf;

	@PostConstruct/*fait partie du déploiement*/
	@Scheduled(fixedDelay = 120_000,/*commence après le déploiement */ initialDelay = 120_000/*et, compte tenu du @PostConstruct :*/)
		// pour le remplissage du cache tous les deux minutes a partir de la deuxieme minutes pcq postconstruct rempli la premiere fois
	@ManagedOperation
	void loadCache(){
		EntityManager em = emf.createEntityManager();
		emf.getMetamodel().getEntities().stream()
				.filter(e -> e.getJavaType().isAnnotationPresent(Cacheable.class))
				.map(e -> "select x from "+e.getName()+" x")
				.forEach(q -> em.createQuery(q).getResultList());
		/*
		Set<EntityType<?>> entities = emf.getMetamodel().getEntities();
		for (EntityType<?> entity : entities) {
			if(entity.getJavaType().isAnnotationPresent(Cacheable.class)) {
				System.out.println(entity.getName());
				String q = "select x from "+entity.getName()+" x";
				em.createQuery(q).getResultList();
			}
		}*/
		em.close();
	}


	public static void main(String[] args) /*just to check if application context is properly configured*/{
//	Web embarquée	changer new AnnotationConfigApplicationContext par SpringApplication.run
//		var ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		var ctx = SpringApplication.run(ApplicationConfig.class, args);

		System.out.println("ctx is open");
		System.out.println(ctx.getBean(DataSource.class));
		System.out.println(ctx.getBean(EntityManagerFactory.class));
	/*	A a = ctx.getBean(A.class);
		System.out.println("***");
		a.foo();
		System.out.println("***");
		a.foo();*/
		System.out.println(ctx.getBean(PlatformTransactionManager.class));

//		System.out.println("closing");
//		ctx.close();
	}
}
