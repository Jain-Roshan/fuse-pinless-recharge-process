package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrepaidBalanceTopUpProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(PrepaidBalanceTopUpProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside PrepaidBalanceTopUpProcessor ==========" + exchange.getIn().getBody(String.class));
		//BalanceTopupRequest balanceTopupRequest = new BuildBalanceTopupRequest();
		exchange.getOut().setBody("");
	}

}