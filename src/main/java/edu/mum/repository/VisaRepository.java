package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long>, CreditCardRepository {
//	List<Visa> findByCardNum(String cardnum);
}
