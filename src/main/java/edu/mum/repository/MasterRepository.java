package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.Master;
import java.lang.String;
import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
	List<Master> findByCardNum(String cardnum);
}
