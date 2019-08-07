package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceInventoryProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(ResourceInventoryProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.debug("==========Inside ResourceInventoryProcessor==========" + exchange.getIn().getBody(String.class));
		//BalanceTopupRequest balanceTopupRequest = new BuildBalanceTopupRequest();
		exchange.getOut().setBody("");
	}

}
