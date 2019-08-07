package com.telkom.service;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/")
public class Service {
	private static Logger logger = LoggerFactory.getLogger(Service.class);

	public Service() {
	}

	@POST
	@Path("/balanceTopUp")
	public String balanceTopUp(String Body) throws JsonParseException, JsonMappingException, IOException {

		logger.info("========== Inside Service balanceTopUp  =========");

		return "Success";
	}
}