package Comcast.KEB.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import Comcast.KEB.domain.Country;

public interface CountryDao extends CrudRepository<Country,String> {
	
}
