package Comcast.KEB.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import Comcast.KEB.domain.Dealer;

public interface PaginationAndSortingDao extends PagingAndSortingRepository<Dealer,Long>
{
	@Query("Select d from Dealer d")
public Page<Dealer> findAllByPage(Pageable page);
	
}
