package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.balanaceTopup.BalanceTopUpResponse;
import com.telkom.balanaceTopup.BalanceTopUpStatusWrapper;

public class PinlessRechargeProcessResponseProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(PinlessRechargeProcessResponseProcessor.class);
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside PinlessRechargeProcessResponseProcessor ==========" + exchange.getIn().getBody(String.class));
		BalanceTopUpResponse balanceTopUpResponse = new BalanceTopUpResponse();
		if("MSISDN Validation failed".equalsIgnoreCase(BalanceTopUpStatusWrapper.BALANCE_TOPUP_STATUS.getStatus()) || "Recharge Validation failed".equalsIgnoreCase(BalanceTopUpStatusWrapper.BALANCE_TOPUP_STATUS.getStatus()) ) {
			balanceTopUpResponse.setCode("999");
		}else {
			balanceTopUpResponse.setCode("201");
		}
		balanceTopUpResponse.setDescription(BalanceTopUpStatusWrapper.BALANCE_TOPUP_STATUS.getStatus());
		
		exchange.getOut().setBody(objectMapper.writeValueAsString(balanceTopUpResponse));
	}

}