package Comcast.KEB.cache;

import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Comcast.KEB.dao.DealerDao;
import Comcast.KEB.domain.Dealer;
import Comcast.KEB.service.DataAccessService;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;


public class CacheLoaderFactory implements BootstrapCacheLoader{
	@Autowired 
	DataAccessService das;
	@Autowired
	DealerDao dlrDao;

private final static Logger logger=
Logger.getLogger(DataAccessService.class.getName());


public boolean isAsynchronous() {
	// TODO Auto-generated method stub
	return false;
}

/*this is to load cache in the startup*/

public void load(Ehcache arg0) throws CacheException {
	// TODO Auto-generated method stub
	logger.info("inside Load method of cacheFactory");
	if(arg0.getName().equals("dealerCache"))
	{
	Iterator<Dealer> iterator =dlrDao.findAll().iterator();

	while(iterator.hasNext()){
		Dealer dlr = iterator.next();
		
		arg0.put(new Element(dlr.getDealerID(), dlr));

	}	
	}
	}


	


//first idhu nadakum then load method
/*@Override
public BootstrapCacheLoader createBootstrapCacheLoader(Properties arg0) {
	// TODO Auto-generated method stub
	return this;
}
*/ /*idu application la eludiyachu*/

public Object clone()
{
	return null;
	}

}
