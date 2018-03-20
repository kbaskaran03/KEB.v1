package Comcast.KEB.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import Comcast.KEB.cache.DealerCache;
import Comcast.KEB.dao.AddiDiscDao;
import Comcast.KEB.dao.CountryDao;
import Comcast.KEB.dao.DealerDao;
import Comcast.KEB.dao.PaginationAndSortingDao;
import Comcast.KEB.dao.ProductDao;
import Comcast.KEB.domain.Country;
import Comcast.KEB.domain.Dealer;
import Comcast.KEB.domain.Prod;
import Comcast.KEB.domain.Product;
import Comcast.KEB.domain.additionalDiscount;
@Component
public class DataAccessService {
@Autowired
	CountryDao dao;
@Autowired
DealerDao daoDealer;
@Autowired
MqPublisher Mqpublish;
@Autowired
KieContainer kieContainer;
@Autowired
ProductDao prodDao;
@Autowired
AddiDiscDao addiDao;
/*@Autowired
DealerCache dlrCache;*/
@Autowired
PaginationAndSortingDao pas;

private final static Logger logger=
Logger.getLogger(DataAccessService.class.getName());



public Product getProductDiscount(Product product) {
	//KieSession kieSession = kieContainer.newKieSession("rulesSession");
	KieSession kieSession = kieContainer.newKieSession("rulesSession");
	//StatelessKieSession kSession = kieContainer.newStatelessKieSession();
	Iterator<additionalDiscount> iterator =addiDao.findAll().iterator();
	HashMap<String, String> test=new HashMap<String, String>();
	while(iterator.hasNext()){
		additionalDiscount disc = iterator.next();
		
		test.put(disc.getTypeProduct(), disc.getDiscProduct());

	}
	/*
	test.put("highspeedinternet","10");
	test.put("lte", "15");*/
	kieSession.setGlobal("discounts", test );
	kieSession.insert(product);
	
	kieSession.fireAllRules();
	kieSession.dispose();
	//kSession.execute(product);
	Product p=new Product();
	p.setProdtypeID(prodDao.findByType(product.getType()).getProdtypeID());
	p.setType(prodDao.findByType(product.getType()).getType());
	String givenDiscount=product.getDiscount();
	String rulesDiscount=prodDao.findByType(product.getType()).getDiscount();
	Long givenDisc=Long.parseLong(givenDiscount);
	Long rulesDisc=Long.parseLong(rulesDiscount);
	Long totalDisc=givenDisc+rulesDisc;
	p.setDiscount(totalDisc.toString());
	prodDao.save(p);
	return product;

}

//for excel
public void getProductDiscountForExcel() {

		KieSession kSession = kieContainer.newKieSession("rulesSession");

		Prod p = new Prod();
		p.setType("highspeedinternet");

		FactHandle fact1;

		fact1 = kSession.insert(p);
		kSession.fireAllRules();

		System.out.println("The discount for the  product "
				+ p.getType() + " is " + p.getDiscount());
				
	
		}



  public Iterable<Country> getAllCountries(){
	return dao.findAll();
}
 

/*  public void updateProduct(Product product) throws JmsException, JsonProcessingException
{
	  
	     
}*/
  
  public void createNewCountry(String countryId,String countryName,String isdCode,String currencyId,String currencyName,
			String currencySymbol,String ipAddress,String userId) throws JmsException, JsonProcessingException
  {
	  
	  Country c=new Country();
	  c.setCountryId(countryId);
	  c.setCountryName(countryName);
	  c.setCurrencyId(currencyId);
	  c.setCurrencyName(currencyName);
	  c.setCurrencySymbol(currencySymbol);
	  c.setEnter_dt_tm(new Timestamp(System.currentTimeMillis()));
	  c.setIpAddress(ipAddress);
	  c.setIsdCode(isdCode);
	  c.setUpdt_dt_tm(new Timestamp(System.currentTimeMillis()));
	  c.setUserId(userId);
      dao.save(c);
     // Mqpublish.sendCountryMessage("keerthiTest", c);
      Mqpublish.sendCountryMessage("keerthiTopic", c);
  }
  
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public void createNewDealerCountry() throws JmsException, JsonProcessingException
{
	  Country c=new Country();
	Dealer d=new Dealer();
	  c.setCountryId("34");
	  c.setCountryName("Alaska");
	  c.setCurrencyId("AL");
	  c.setCurrencyName("ALZ");
	  c.setCurrencySymbol("*");
	  c.setEnter_dt_tm(new Timestamp(System.currentTimeMillis()));
	  c.setIpAddress("5.6.9");
	  c.setIsdCode("");
	  c.setUpdt_dt_tm(new Timestamp(System.currentTimeMillis()));
	  c.setUserId("admin");
    dao.save(c);
    logger.info("country updated");
    //Mqpublish.sendCountryMessage("keerthiTest", c);
    Mqpublish.sendCountryMessage("keerthiTopic", c);
    d.setDealerID(67);
    d.setCustomerName("fortz");
    d.setFirstName("Miller");
    d.setLastName("kroze");
    d.setCountryID(c); // mapping paru... object dan vechu map panuvom.. anga DB la foreign key la set pananu la avasyam illa
    daoDealer.save(d);
    }

  public void deleteEntry(String countryID)
  {
	  dao.delete(countryID);
  }
  
  public Country findCountryByID(String countryID)
  {
	  return dao.findOne(countryID);
  }
  
  @Cacheable("dealerCache")
  /*still not cloned add copy on read attribute in xml*/
  public Dealer findDelaerByID(long dealerID)
  {
	    logger.info("inside dealercache");
	  return daoDealer.findOne(dealerID);
  //DealerCache dlrCache;
	  //System.out.println(dlrCache.findOne(dealerID).getCustomerName());
	  //return dlrCache.findOne(dealerID);

  
	  
  }
  public Iterable<Dealer> findDealerByPage(int pagenum)
  {

	  PageRequest page=new PageRequest(pagenum, 2);
	  Page<Dealer> result=pas.findAll(page);
	  System.out.println(result.getTotalElements());
	  System.out.println(result.getTotalPages());
	  return result.getContent();
  }
	  

	public List<Dealer> findByCountry(String country_id)
	{
		return daoDealer.findByCountry(country_id);
	}
  
  public Iterable<Dealer> getAllDealer(){
	return daoDealer.findAll();
}
  	
}
