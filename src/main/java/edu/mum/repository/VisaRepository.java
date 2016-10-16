package edu.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mum.model.Visa;


@Repository
public interface VisaRepository extends JpaRepository<Visa, Long>, CreditCardRepository {

//@Repository
//public interface VisaRepository extends JpaRepository<Visa, Long> {
//	@Query("Select v from Visa v where v.cardNum=:cardNumber")
//	Visa findByCardNum(@Param("cardNumber") String cardnum);
}
