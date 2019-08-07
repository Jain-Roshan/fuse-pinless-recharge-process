package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.balanaceTopup.BalanceTopUpStatus;
import com.telkom.balanaceTopup.BalanceTopUpStatusWrapper;

public class BalanceTopUpStatusRechargeValidationProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(BalanceTopUpStatusRechargeValidationProcessor.class);
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside BalanceTopUpStatusRechargeValidationProcessor ==========" + exchange.getIn().getBody(String.class));
		Integer responseCode = (Integer) exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
		BalanceTopUpStatus balanceTopUpStatus  = new BalanceTopUpStatus();
		balanceTopUpStatus.setStatus("Recharge Validation failed");
		//TODO remove when actual end point is configured
		responseCode = 201;
		if (responseCode != null && responseCode == 201) {
			balanceTopUpStatus.setStatus("Recharge Validation Successful");
		}
		BalanceTopUpStatusWrapper.BALANCE_TOPUP_STATUS = balanceTopUpStatus;
		
		exchange.getOut().setBody(objectMapper.writeValueAsString(balanceTopUpStatus));
	}

}
