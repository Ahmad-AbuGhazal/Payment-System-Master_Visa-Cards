package edu.mum.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mum.model.MasterTransactionRecord;
import edu.mum.model.TransactionRecord;

@Repository
public interface MasterTransactionRecordRepository extends JpaRepository<MasterTransactionRecord, Long>, CreditCardTransactionRecordRepository {
	@Override
	public default List<TransactionRecord> findAllTransactionRecordRepository(){
		return new ArrayList<TransactionRecord>(findAll());
	}
}
