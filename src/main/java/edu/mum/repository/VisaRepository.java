package edu.mum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {	
	List<Visa> findByCardNum(String cardnum);
}
