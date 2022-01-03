package com.banking.bankbalanceValidation.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.banking.bankbalanceValidation.entity.BalanceReport;

public interface BalanceReportsRepositiory extends JpaRepository <BalanceReport, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE addamount u SET u.balance = balance - :balance WHERE u.accountno = :accountno")
	Integer updatebalance(int balance, int accountno);
	
}
