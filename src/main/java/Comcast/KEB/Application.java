	package Comcast.KEB;


import javax.jms.ConnectionFactory;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import Comcast.KEB.cache.CacheLoaderFactory;
import Comcast.KEB.domain.Country;
import Comcast.KEB.service.DataAccessService;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
public class Application implements CommandLineRunner {
	public static void main(String Args[])
	{
		SpringApplication.run(Application.class,Args);
	}
	@Autowired
	DataAccessService dao;
	
	@Bean
	public ObjectMapper messageConverter()
	{
		ObjectMapper mapper=new ObjectMapper();
		return mapper;
	}
	
/*	@Bean
	public KieContainer kieContainer() {

        KieServices kieServices = KieServices.Factory.get();
        // Create a module model
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        // Base Model from the module model
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel( "KBase" )
                .setDefault( true )
                .setEqualsBehavior( EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode( EventProcessingOption.STREAM );                

        // Create session model for the Base Model
        KieSessionModel ksessionModel = kieBaseModel.newKieSessionModel( "KSession" )
                .setDefault( true )
                .setType( KieSessionModel.KieSessionType.STATEFUL )
                .setClockType( ClockTypeOption.get("realtime") );

        // Create File System services
        KieFileSystem kFileSystem = kieServices.newKieFileSystem();
        //File file = new File();
        Resource resource = kieServices.getResources().newFileSystemResource("src/main/resources/rules/rules.drl").setResourceType(ResourceType.DRL);
    kFileSystem.write( resource );       

        KieBuilder kbuilder = kieServices.newKieBuilder( kFileSystem );
        // kieModule is automatically deployed to KieRepository if successfully built.
        kbuilder.buildAll();

        if (kbuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            throw new RuntimeException("Build time Errors: " + kbuilder.getResults().toString());
        }
        KieContainer kContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
       
        return kContainer;		
	}*/
	
	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}

	
	@Bean
	public MessageConverter message()
	{
		MappingJackson2MessageConverter convertor = new  MappingJackson2MessageConverter();
		convertor.setTargetType(MessageType.TEXT);
		convertor.setTypeIdPropertyName("_type");
		convertor.setObjectMapper(messageConverter());
		return convertor;
	}
	
	@Bean
	public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory,
	                                                           DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setPubSubDomain(true);
	    //listening ku matum dan
	    configurer.configure(factory, connectionFactory);
	    return factory;
	}
	
	//ehcacheBeans
	@Bean
	public CacheManager cacheManager() {
	    return new EhCacheCacheManager(cacheMangerFactory().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean cacheMangerFactory() {
	    EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
	    bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
	    bean.setShared(true);

	    return bean;
	}

	@Bean
	public EhCacheFactoryBean ehCacheFactory() {
	    EhCacheFactoryBean ehCacheFactory = new EhCacheFactoryBean();
	    ehCacheFactory.setCacheManager(cacheMangerFactory().getObject());
	    ehCacheFactory.setBootstrapCacheLoader(bootstrap());
	    return ehCacheFactory;
	}

	@Bean
	public BootstrapCacheLoader bootstrap(){
	    return new CacheLoaderFactory();
	}

	
	
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Country c;	
	//c=dao.findCountryByID("US");
	
	//dao.createNewDealerCountry();
	//dao.getProductDiscountForExcel();
		
		System.out.println(dao.findDealerByPage(100));
		System.out.println(dao.findDealerByPage(2));
		
				
				//System.out.println(c.getCountryName());
				
	

				
	}

}
