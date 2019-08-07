package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.balanaceTopup.BalanceTopUpStatus;
import com.telkom.balanaceTopup.BalanceTopUpStatusWrapper;

public class BalanceTopUpStatusMSISDNValidationProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(BalanceTopUpStatusMSISDNValidationProcessor.class);
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside BalanceTopUpStatusMSISDNValidationProcessor ==========");
		Integer responseCode = (Integer) exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
		//TODO remove when actual end point is configured
		responseCode = 201;
		BalanceTopUpStatus balanceTopUpStatus  = new BalanceTopUpStatus();
		balanceTopUpStatus.setStatus("MSISDN Validation failed");
		if (responseCode != null && responseCode == 201) {
			logger.info("==========Inside if Waiting for Validation");
			balanceTopUpStatus.setStatus("Waiting for Validation");
		}
		BalanceTopUpStatusWrapper.BALANCE_TOPUP_STATUS = balanceTopUpStatus;
		
		//TODO remove commented code when actual end point is configured
		//getSubscribersResponse.GetSubscribersReply.getSubscribersResult.subscriberStatus and prepaidFlag
		//check subscriberStatus="B01" OR "B03" and prepaidFlag = "0" OR "3"  and set true value to topup else false
		//if(("B01".equalsIgnoreCase(subscriberStatus) ||  "B03".equalsIgnoreCase(subscriberStatus)) && ("0".equalsIgnoreCase(prepaidFlag) ||  "3".equalsIgnoreCase(prepaidFlag))) {
			exchange.getIn().setHeader("MSISDN", "true");
		//}else{
			//exchange.getIn().setHeader("MSISDN", "false");
	     //}
			
		exchange.setProperty("topupResponseCode", responseCode);
		exchange.getIn().setBody(objectMapper.writeValueAsString(balanceTopUpStatus));
	}

}
