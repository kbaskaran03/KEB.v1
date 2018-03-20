package Comcast.KEB.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Comcast.KEB.dao.DealerDao;
import Comcast.KEB.domain.Dealer;

@Component

public class DealerCache {

@Autowired
DealerDao dlrDao;
HashMap<Long, Dealer> testDealer=new HashMap<Long, Dealer>();
@PostConstruct
public void populate()
{
	Iterator<Dealer> iterator =dlrDao.findAll().iterator();

	while(iterator.hasNext()){
		Dealer dlr = iterator.next();
		
		testDealer.put(dlr.getDealerID(), dlr);

	}	
}


	
	public Dealer findOne(Long id)
	{
		return testDealer.get(id);
		
	}
	
}
