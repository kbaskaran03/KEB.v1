package Comcast.KEB.web;


//import java.sql.Timestamp;
import java.util.List;

//import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import Comcast.KEB.domain.Country;
import Comcast.KEB.domain.Dealer;
import Comcast.KEB.domain.Product;
import Comcast.KEB.service.DataAccessService;
//import Comcast.KEB.service.JewelleryShopService;
//import Comcast.KEB.service.MqPublisher;

@RestController
@RequestMapping(path="/Countries")
public class MyRestService {
@Autowired
DataAccessService dao;
@Autowired
DataAccessService daoDealer;
@Autowired
DataAccessService kieContainer;


@RequestMapping(path="/getDiscount/{type}/" ,produces=MediaType.APPLICATION_JSON_VALUE)
public Product getQuestions(@PathVariable("type") String type) {

	Product product = new Product();
	product.setType(type);
	return kieContainer.getProductDiscount(product);

}

@RequestMapping(path="/country/{countryID}/" ,produces=MediaType.APPLICATION_JSON_VALUE)
public Country getOneCountry(@PathVariable("countryID") String countryId)
{
	
	return dao.findCountryByID(countryId);}


@RequestMapping(path="/dealer/{dealerID}/" ,produces=MediaType.APPLICATION_JSON_VALUE)
public Dealer findOneDealer(@PathVariable("dealerID") Long dealerID)
{
	return daoDealer.findDelaerByID(dealerID);
	}



@RequestMapping(path="/create",method=RequestMethod.POST)
public Country createCountry(@RequestParam("id") String countryId,@RequestParam(name="countryname",required=false)String countryName,
		@RequestParam("isdcode")String isdCode,@RequestParam("currencyID")String currencyId,
		@RequestParam("currencyname")String currencyName,@RequestParam("currencysymbol")
		String currencySymbol,@RequestParam("ipaddress")String ipAddress,@RequestParam("userID")String userId) throws JmsException, JsonProcessingException
{	
	dao.createNewCountry(countryId,	countryName,isdCode,currencyId,currencyName,currencySymbol,
	ipAddress,userId);
	
	return dao.findCountryByID(countryId);

}

@RequestMapping(path="/createUsingJSON",method=RequestMethod.POST)
public void createCountry(@RequestBody Country c) throws JmsException, JsonProcessingException
{
	 dao.createNewCountry(c.getCountryId(), c.getCountryName(), c.getIsdCode(), c.getCurrencyId(), c.getCurrencyName(), c.getCurrencySymbol(), c.getIpAddress(), c.getUserId());
}

@GetMapping(path="/findDealersByCountry/{countryID}/" ,produces=MediaType.APPLICATION_JSON_VALUE)
public List<Dealer> findByCountry(@PathVariable("countryID") String countryId)
{
	return daoDealer.findByCountry(countryId);
		}


/*
@GetMapping(path="/all" ,produces=MediaType.APPLICATION_JSON_VALUE)
public Country findOne()
{
	return dao.findCountryByID("US");
		}
*/
/*@GetMapping(path="/alldealer" ,produces=MediaType.APPLICATION_JSON_VALUE)
public Dealer findOneDealer()
{
	return daoDealer.findDelaerByID(67);
		}

*/

/*@GetMapping(path="/findDealersByCountry" ,produces=MediaType.APPLICATION_JSON_VALUE)
public List<Dealer> findByCountry()
{
	return daoDealer.findByCountry("IN");
		}
*/

}
