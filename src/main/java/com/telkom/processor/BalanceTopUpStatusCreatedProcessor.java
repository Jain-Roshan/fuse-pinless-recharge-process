package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.balanaceTopup.BalanceTopUpStatus;

public class BalanceTopUpStatusCreatedProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(BalanceTopUpStatusCreatedProcessor.class);
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside BalanceTopUpStatusCreatedProcessor ==========");
		BalanceTopUpStatus balanceTopUpStatus  = new BalanceTopUpStatus();
		balanceTopUpStatus.setStatus("Created");
		exchange.getIn().setBody(objectMapper.writeValueAsString(balanceTopUpStatus));
	}

}