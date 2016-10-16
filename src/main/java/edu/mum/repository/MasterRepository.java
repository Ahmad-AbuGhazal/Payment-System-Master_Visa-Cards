package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long>, CreditCardRepository {
//	List<Master> findByCardNum(String cardnum);
	
}
