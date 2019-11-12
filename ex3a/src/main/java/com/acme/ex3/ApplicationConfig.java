package com.acme.ex3;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;

import com.acme.common.EnableSlf4j;
import com.acme.common.service.impl.CommandProcessorImpl;
import com.acme.common.service.impl.DefaultExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootApplication // implique @EnableAutoConfiguration
// sans spring boot :
 //@PropertySource("classpath:application.properties")
 //@EnableTransactionManagement(proxyTargetClass = true)
 //@Configuration
 //@EnableJpaRepositories
 //@ComponentScan
@EnableScheduling
@ManagedResource
@EnableSlf4j
@Import({CommandProcessorImpl.class, DefaultExceptionHandler.class})
public class ApplicationConfig {

	private final Environment env;

	public ApplicationConfig(Environment env) {
		this.env = env;
	}

	/* sans Spring Boot :
	@Bean
	DataSource ds() {
		HikariDataSource ds = new HikariDataSource();
		// TODO : valoriser les propriétés driverClassName, username et jdbcUrl.
		//Function<String, String> prop = env::getRequiredProperty;
		ds.setUsername(env.getRequiredProperty("datasource.username"));
		ds.setDriverClassName(env.getRequiredProperty("datasource.driver-class-name"));
		ds.setJdbcUrl(env.getRequiredProperty("datasource.url"));
		System.out.println(this.getClass());
		return ds;
	}

	@Bean
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
	PlatformTransactionManager txManager(EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}
	*/

	@Autowired
	private EntityManagerFactory emf;

	@PostConstruct @Order(1) /*fait partie du déploiement*/
	@Scheduled /*commence après le déploiement */(
			fixedDelay = 120_000,
			/*et, compte tenu du @PostConstruct :*/
			initialDelay = 120_000
	)
	@ManagedOperation
	void loadCache(){
		EntityManager em = emf.createEntityManager();
		emf.getMetamodel().getEntities().stream()
				.filter(entityType -> entityType.getJavaType().isAnnotationPresent(Cacheable.class))
				.map(entityType -> "select x from "+entityType.getName()+" x")
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

	@Bean
	public WebMvcConfigurer configurer(){
		Formatter<LocalDate> localDateFormatter = new Formatter<LocalDate>() {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			@Override
			public LocalDate parse(String text, Locale locale) throws ParseException {
				return StringUtils.hasText(text) ? LocalDate.parse(text, df) : null;
			}

			@Override
			public String print(LocalDate object, Locale locale) {
				return object==null ? "" : object.format(df);
			}
		};
		return new WebMvcConfigurer() {
			@Override
			public void addFormatters(FormatterRegistry registry) {
				registry.addFormatter(localDateFormatter);
			}
		};
	}
	public static void main(String[] args) /*just to check if application context is properly configured*/{
		var ctx = SpringApplication.run(ApplicationConfig.class, args);
		System.out.println("ctx is open");
		System.out.println(ctx.getBean(DataSource.class));
		System.out.println(ctx.getBean(EntityManagerFactory.class));
		System.out.println(ctx.getBean(PlatformTransactionManager.class));
	}

	/*
	@Autowired
	private BookDocumentRepository esRepository;

	@Autowired
	private ObjectMapper jackson;

	@PostConstruct
	void fillElasticSearchIndex() throws IOException {
		InputStream json = getClass().getResourceAsStream("/es-data.json");
		BookDocument[] documents = jackson.readValue(json, BookDocument[].class);
		System.out.println(documents.length);
		esRepository.saveAll(Arrays.asList(documents));
	}*/



}
