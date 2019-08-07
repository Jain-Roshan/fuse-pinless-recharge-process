package com.telkom.route;

import java.net.UnknownHostException;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telkom.processor.AfterMSISDNValidationBalanceTopUpProcessor;
import com.telkom.processor.BalanceTopUpProcessor;
import com.telkom.processor.BalanceTopUpStatusCreatedProcessor;
import com.telkom.processor.BalanceTopUpStatusMSISDNValidationProcessor;
import com.telkom.processor.BalanceTopUpStatusRechargeValidationProcessor;
import com.telkom.processor.LogicalResourceInventoryProcessor;
import com.telkom.processor.PinlessRechargeProcessResponseProcessor;

public class PinlessRechargeProcessRoute extends RouteBuilder{

	public static final Logger looger = LoggerFactory.getLogger(PinlessRechargeProcessRoute.class); 
	@Override
	public void configure() throws Exception {
		onException(UnknownHostException.class).handled(true).log("VPN not connected.").transform()
		.simple("VPN not connected.");

        from("cxfrs://bean://restService")
        .log(" ==== Enter into  PinlessRechargeProcessRoute")
        .process(new BalanceTopUpProcessor()) //.to("direct:balanceTopup")
		.process(new BalanceTopUpStatusCreatedProcessor()) //.to("direct:balanceTopUpStatus")
		.process(new LogicalResourceInventoryProcessor())//.to("direct:logicalResourceInventory")
		.log(" ==== Response from LogicalResourceInventoryProcessor ==== ${header.CamelHttpResponseCode}")
		.process(new BalanceTopUpStatusMSISDNValidationProcessor())
		 //Validate Response MSISDN status = ‘Active’ or ‘Suspended’ and Account Type = Prepaid or Hybrid
		.choice().when(header("MSISDN").isEqualTo("true")) 
		    			//.to("direct:balanceTopUpStatus")
		    			.process(new AfterMSISDNValidationBalanceTopUpProcessor())
		    			//.to("direct:balanceTopup")
		    			.log(" ==== Response from balanceTopup ==== ${header.CamelHttpResponseCode}")
		    			.process(new BalanceTopUpStatusRechargeValidationProcessor())
		    			//.to("direct:balanceTopUpStatus")
		    	 		.process(new PinlessRechargeProcessResponseProcessor())
		.otherwise().to("direct:balanceTopUpStatus").process(new PinlessRechargeProcessResponseProcessor()).end();		
		
        
        from("direct:balanceTopup").log(" ==== POST balanceTopUp CRM==== ").removeHeaders("*").removeProperties("*")
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.recipientList(simple("direct:{{balancetopup}}"));
        
        from("direct:balanceTopUpStatus").log(" ==== put message to balanceTopUp status CRM==== ").removeHeaders("*").removeProperties("*")
        .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
        .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
		.recipientList(simple("direct:{{balancetopupstatus}}"));

        from("direct:logicalResourceInventory").log(" ==== get logicalResourceInventory huawei service==== ").removeHeaders("*").removeProperties("*")
        .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.recipientList(simple("direct:{{logicalResourceInventory}}"));
        
	}

}
