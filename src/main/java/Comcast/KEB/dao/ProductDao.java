package Comcast.KEB.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Comcast.KEB.domain.Product;

public interface ProductDao extends CrudRepository<Product, Long> {
	@Query("Select d from Product d where type=?1")
	public Product findByType(String type);
}
