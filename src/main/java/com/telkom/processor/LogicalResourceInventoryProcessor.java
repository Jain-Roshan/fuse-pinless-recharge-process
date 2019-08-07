package com.telkom.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telkom.balanaceTopup.BalanceTopupWrapper;

public class LogicalResourceInventoryProcessor implements Processor{

	private static Logger logger = LoggerFactory.getLogger(LogicalResourceInventoryProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("==========Inside Logical ResourceInventory Processor to get subscriber==========" + exchange.getIn().getBody(String.class));
		String id = BalanceTopupWrapper.BALANCE_TOPUP.getProduct().getId();
		//getSubscribers.GetSubscribersRequest.msisdn = id
		exchange.getOut().setBody(id);
	}

}