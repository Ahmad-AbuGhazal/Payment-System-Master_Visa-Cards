package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.Visa;
import java.lang.String;
import java.util.List;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
	List<Visa> findByCardNum(String cardnum);
}
