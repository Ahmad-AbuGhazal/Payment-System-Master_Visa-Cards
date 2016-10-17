package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mum.model.Master;

import java.lang.String;
import java.util.List;
@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
//	 @Query("Select m from Master m where m.cardNum=:cardNumber")
//	 Master findByCardNum(@Param("cardNumber") String cardnum);
	
	List<Master> findByCardNum(String cardnum);
}
