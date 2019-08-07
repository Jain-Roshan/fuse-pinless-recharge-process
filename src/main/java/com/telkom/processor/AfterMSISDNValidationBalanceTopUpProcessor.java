package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkom.balanaceTopup.BalanceTopupWrapper;

public class AfterMSISDNValidationBalanceTopUpProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(AfterMSISDNValidationBalanceTopUpProcessor.class);
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside AfterMSDINValidationBalanceTopUpProcessor ==========" + exchange.getIn().getBody(String.class));
		/*BalanceTopup balanceTopUp  = null;
		
		if(BalanceTopupWrapper.BALANCE_TOPUP == null) {
			logger.info("==========Set value to BalanceTopupWrapper.BALANCE_TOPUP ==========");
			balanceTopUp = objectMapper.readValue(exchange.getIn().getBody(String.class), BalanceTopup.class);
			BalanceTopupWrapper.BALANCE_TOPUP = balanceTopUp;
		}else {
			logger.info("==========ReSet value to BalanceTopupWrapper.BALANCE_TOPUP ==========");
			balanceTopUp = BalanceTopupWrapper.BALANCE_TOPUP;
		}
		
		logger.info("==========Exit from BalanceTopUpProcessor ==========");*/
		exchange.getOut().setBody(objectMapper.writeValueAsString(BalanceTopupWrapper.BALANCE_TOPUP));
	}
}

