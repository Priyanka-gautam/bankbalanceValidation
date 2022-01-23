package com.banking.bankbalanceValidation.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.bankbalanceValidation.entity.BalanceReport;

public interface BalanceReportsRepositiory extends JpaRepository<BalanceReport, Long> {

	@Query(value = "select userid from useraccount u where u.accountno =:Hnumber", nativeQuery = true)
	Integer getuserid(@Param("Hnumber") int Hnumber);

	@Query(value = "select accountno from useraccount u where u.accountno =:Anumber", nativeQuery = true)
	Integer getaccountnumber(@Param("Anumber") int Anumber);

	@Query(value = "select recieveraccountno from addaccounts u where u.recieveraccountno =:Bnumber", nativeQuery = true)
	Integer getreceiveraccountnumber(@Param("Bnumber") int Bnumber);

	@Query(value = "select balance from useraccount u where u.accountno =:Cnumber", nativeQuery = true)
	Integer getdeposit(@Param("Cnumber") int Cnumber);

	@Transactional
	@Modifying
	@Query(value = "update useraccount u set u.balance = balance - :balances where u.accountno = :accountnos", nativeQuery = true)
	Integer updatebalances(@Param("balances") int balances, @Param("accountnos") int accountnos);

}
