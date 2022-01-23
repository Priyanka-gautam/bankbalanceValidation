package com.banking.bankbalanceValidation.consumer;

import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.banking.bankbalanceValidation.config.BankValidationProducerConfig;
import com.banking.bankbalanceValidation.repository.BalanceReportsRepositiory;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class TransactionConsumer {
 
	@Autowired
	private BankValidationProducerConfig bankvalidationproducer;
	
	@Autowired
	private BalanceReportsRepositiory updatebalance;

	@KafkaListener(topics = "bank2")
	public void consume(String message) {
		System.out.println(message);
		JSONObject json = new JSONObject(message);
		System.out.println(json.get("AccountNumber").toString());
		String saccountno = json.get("AccountNumber").toString();
		int validaccno = Integer.parseInt(saccountno);
		int searchaccno = 0;
		String raccountno = json.get("RAccountNumber").toString();
		int validraccno = Integer.parseInt(raccountno);
		int searchraccno = 0;
		String jamount = json.get("balance").toString();
		int validamount = Integer.parseInt(jamount);
		int samount = 0;
		String report;
		
		// updatebalance.updatebalances(validamount,validaccno);
		int getuserid;
		try {
			searchaccno = updatebalance.getaccountnumber(validaccno);
			searchraccno = updatebalance.getreceiveraccountnumber(validraccno);
			samount = updatebalance.getdeposit(validaccno);
		} catch (Exception e) {
			System.out.println("Not Matched" + e.getMessage());
		}
		System.out.println("Accno" + searchaccno);
		System.out.println("RAccno" + searchraccno);
		System.out.println("deposit" + samount);
		System.out.println("validamount" + validamount);
		int sumamount = 0;
		report = null;
		if (validaccno == searchaccno && searchraccno == validraccno && samount >= validamount) {
			sumamount = samount - validamount;
			report = "Transfered";
			updatebalance.updatebalances(validamount, validaccno);
		} else if (searchaccno == 0) {
			report = "not Transfered Sending accountno not matched";
		}
		else if (searchraccno == 0) {
			report = "not Transfered Reciver accountno not matched";
		}
		else {
			report = "not enough Balance";
		}
		System.out.println("Message: " + report);
		transactionproducer(String.valueOf(validaccno), String.valueOf(validraccno), report,
				String.valueOf(validamount));
	}
	public void transactionproducer(String sac, String rac, String message, String validamount) {
		Properties properties = new Properties();
		// kafka bootstrap server
		
		Producer<String, String> producer = new KafkaProducer<>(bankvalidationproducer.Bankvalidationproducer());
		try {
			producer.send(newRandomTransaction(sac, rac, message, validamount));
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		producer.close();
	}

	public static ProducerRecord<String, String> newRandomTransaction(String accno, String rno, String report,
			String validamount) {
		// creates an empty json {}
		ObjectNode transactionr = JsonNodeFactory.instance.objectNode();
		Instant now = Instant.now();

		// we write the data to the json document
		transactionr.put("SenderAccountnumber", accno);
		transactionr.put("ReceiverAccountnumber", rno);
		transactionr.put("Report", report);
		transactionr.put("Amountb", validamount);
		transactionr.put("time", now.toString());
		return new ProducerRecord<>("bank3", "1", transactionr.toString());
	}
}