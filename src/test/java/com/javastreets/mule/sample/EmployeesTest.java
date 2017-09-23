package com.javastreets.mule.sample;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class EmployeesTest extends FunctionalMunitSuite {

	@Rule
	public WireMockRule wireMock = new WireMockRule(9005);
	
	@Test
	public void testGetEmployee() throws Exception{
		wireMock.stubFor(get(urlPathEqualTo("/api/employees"))
							.willReturn(aResponse()
											.withBody("[{\"id\": 1, \"name\": \"Manik\"},"
																		+ "{\"id\": 2, \"name\": \"John\"}]")
											.withHeader("Content-type", "application/json")));
		
		MuleEvent returnEvent = runFlow("getEmployees", testEvent("dummy"));
		
		String employees = returnEvent.getMessage().getPayloadAsString();
		
		Assert.assertNotNull(employees);
	}
	
}
