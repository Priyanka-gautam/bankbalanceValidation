package com.banking.bankbalanceValidation.consumer;

import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
	
	@KafkaListener(topics = "bank2")
    public void consume(String message) {
		System.out.println(message);
		
		JSONObject json = new JSONObject(message);
        System.out.println(json.get("AccountNumber").toString());
        
        
        String saccountno = json.get("AccountNumber").toString();

        int validaccno=Integer.parseInt(saccountno);
        
        int searchaccno=0;
        
        
        String raccountno = json.get("RAccountNumber").toString();

        int validraccno=Integer.parseInt(raccountno);
        
        int searchraccno=0;
        
        
        String jamount = json.get("balance").toString();

        int validamount=Integer.parseInt(jamount);
        
        int samount=0;
        
        
        String report;
        
		
		}
}