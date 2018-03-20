package Comcast.KEB.dao;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Comcast.KEB.domain.Country;
import Comcast.KEB.domain.Dealer;
import io.swagger.annotations.Scope;

@org.springframework.context.annotation.Scope(scopeName="request")
public interface DealerDao extends CrudRepository<Dealer,Long> {
	@Query("Select d from Dealer d where country_id=?1")
	public List<Dealer> findByCountry(String country_id);
}
